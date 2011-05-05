package com.jts.guice.factory.example;

public interface FooFactory {
	Foo create(boolean isBar, boolean useAnnotation);
}
