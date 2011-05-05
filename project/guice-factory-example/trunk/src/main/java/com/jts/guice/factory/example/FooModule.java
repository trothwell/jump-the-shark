package com.jts.guice.factory.example;

import com.google.inject.AbstractModule;

public class FooModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(FooFactory.class).to(FooFactoryImpl.class);
		bind(Foo.class).annotatedWith(Bar.class).to(FooBar.class);
		bind(Foo.class).annotatedWith(Baz.class).to(FooBaz.class);
	}
}
