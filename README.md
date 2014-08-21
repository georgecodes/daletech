
[![Build Status](https://travis-ci.org/georgecodes/daletech.png?branch=master)](https://travis-ci.org/georgecodes/daletech)

## Introduction
Daletech is a small Java library that facilitates the use of Groovy code to build DSLs that can configure and interact with your Java code. It evolved out of a sprinkling of classes I wrote in another project, which I realised could be generalised and used elsewhere. At the core, Daletech is just a Groovy script parser that accepts a general-purpose, easily extensible delegate for those scripts.

### The 'Hello, world!'

No introduction would be complete without a Hello, world example, so let's get to it. We're going to create a DSL that allows the script to say hello. Here's the entire script:

    sayHello()

World-beating, I"m sure you'll agree. The code to achieve this magic?

    ConfigurableGroovyDelegate delegate = new ConfigurableGroovyDelegate();
    DelegatingGroovyScriptParser parser = new DelegatingGroovyScriptParser(delegate);

    HelloWorldPlugin hello = new HelloWorldPlugin();
    delegate.registerPlugin(hello);
    
    parser.parseFromClasspath("hello.groovy");

    assert "Hello, world!".equals(hello.getMessage());


The ConfigurableGroovyDelegate is bound to the Groovy script as a delegate, and the DelegatingGroovyScriptParser does the heavy lifting of reading in the script and plumbing things together. These classes are provided by Daletech. The HelloWorldPlugin is where our actual DSL parsing is done. Here's what it looks like:

    import com.elevenware.daletech.ActionDelegate;
    import com.elevenware.daletech.ActionPlugin;

	public class HelloWorldPlugin extends ActionPlugin {
    
    	private String message;

	    @ActionDelegate
	    public void sayHello() {
	        message = "Hello, world!";
	    }

	    public String getMessage() {
	        return message;
	    }
	}

The annotation @ActionDelegate denotes a method that can be delegated to from your scripts. What you do with those method calls is up to you. In this case, we simply record the message 'Hello, world!' for later retrieval, but these delegate methods can return values and objects, and take parameters, which can be used flexibly in a number of ways. As we'll see later, the ability to accept Groovy Closures as method parameters can open up some interesting possibilities.

## Can't I just do this with ordinary classes?

Yep. Of course. There's really nothing amazing going on here. In fact, Daletech happened completely by accident. I was implementing a simple DSL in another project, and found I was constantly adding new methods to the delegate class. In the spirit of the 	[open/closed principle](http://en.wikipedia.org/wiki/Open/closed_principle) I shunted these methods out into other classes, and plugged them into the delegate object at runtime. At that point, I realised I had the same problem in a couple of areas of the code, and by accident had made the entire solution a lot simpler.

## Getting hold of Daletech

### Sources

You can grab the code from [GitHub](https://github.com/georgecodes/daletech) and build it using 

    $ gradle install

There is currently a POM in there, but I don't guarantee it works, and it's days are numbered.

### Using Maven

You can grab it from Sonatype's Maven repository by first configuring the repo

    <repositories>
      <repository>
        <id>oss-sonatype</id>
        <name>oss-sonatype</name>
        <url>https://oss.sonatype.org/content/repositories/snapshots/</url>
        <snapshots>
            <enabled>true</enabled>
        </snapshots>
      </repository>
    </repositories>

then declaring the dependency

    <dependency>
      <groupId>com.elevenware</groupId>
      <artifactId>daletech</artifactId>
      <version>1.0-SNAPSHOT</version>
    </dependency>


### Using Gradle

    repositories {
        mavenRepo urls: 'http://repository.sonatype.org/content/groups/public/'
    }

    dependencies {
    	runtime "com.elevenware:daletech:1.0-SNAPSHOT"
    }

### Maven Central

Daletech will eventually make its way into maven central, when I release it.

## A more advanced example

The examples I'm using are actually part of the suite of unit tests for Daletech, by the way.

For a slightly more advanced example, using passed arguments and closures, we're going to build a DSL that configures a - non-functional - IoC container. Here's the DSL

    beans {

        bean("helloService", class: "com.elevenware.daletech.lessons.language.beans.HelloServiceImpl")

        bean("messageFactory", class: "com.elevenware.daletech.lessons.language.beans.MessageFactoryImpl") {
            ref('helloService')
        }
    }

What's going on here is, we are declaring a bunch of beans at the top, then declaring individual instances, giving them a name, and a class which will be the concrete class for that bean. The second bean takes an instance of the first bean as a constructor argument. The implementation of the classes in question isn't relevant, and in fact they needn't even actually exist. We're going to first define a little bean definition class to represent configured beans:

    public class LittleBeanDefinition {

        public LittleBeanDefinition(String name, String className) {
            this.name = name;
            this.className = className;
            this.constructorRefs = new ArrayList<>();
        }

        public void addConstructorRef(LittleRef ref) {
            this.constructorRefs.add(ref);
        }

        public String getName() {
            return name;
        }

        public String getClassName() {
            return className;
        }

        public List<LittleRef> getConstructorRefs() {
            return constructorRefs;
        }

        public static class LittleRef {
            private final String name;

            public LittleRef(String name) {
                this.name = name;
            }

            public String getName() {
                return name;
            }
        }
    }

and then an ActionPlugin class to configure them:

    public class BeansPlugin extends ActionPlugin {

        private Map<String, LittleBeanDefinition> beans;
        private LittleBeanDefinition current;

        public  BeansPlugin() {
            beans = new HashMap<>();
        }

        @ActionDelegate
        public void beans(Closure closure) {
            closure.setDelegate(this);
            closure.call();
        }

        @ActionDelegate
        public void bean(Map args, String name) {
            String className = (String) args.get("class");
            LittleBeanDefinition beanDefinition = new LittleBeanDefinition(name, className);
            beans.put(name, beanDefinition);
            current = beanDefinition;
        }

        @ActionDelegate
        public void bean(Map args, String name, Closure closure) {
            String className = (String) args.get("class");
            LittleBeanDefinition beanDefinition = new LittleBeanDefinition(name, className);
            beans.put(name, beanDefinition);
            current = beanDefinition;
            closure.setDelegate(this);
            closure.setResolveStrategy(Closure.DELEGATE_FIRST);
            closure.call();
        }

        @ActionDelegate
        public void ref(String name) {
            current.addConstructorRef(new LittleBeanDefinition.LittleRef(name));
        }

        public Collection<LittleBeanDefinition> getBeans() {
            return beans.values();
        }
    }

Wiring that plugin class into the usual configurable delegate, and the parser, and you're away. Once the script has run, you can query the plugin for the configured beans, and with a little reflection magic you'd have a simple working constructor-injection based IoC container, complete with Groovy DSL for configuration. 

You'll notice that in the two bean methods, I put the Map argument at the start, despite it not being the first argument passed. This is a little Groovy gotcha where implicit map parameters are always put at the start of the argument list.
