package com.jts.guice.factory.example;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class FooFactoryImpl implements FooFactory {
	private Provider<FooBaz> bazProvider;
	private Provider<FooBar> barProvider;
	private Provider<Foo> fooBazProvider;
	private Provider<Foo> fooBarProvider;

	@Inject
	FooFactoryImpl(Provider<FooBar> barProvider, Provider<FooBaz> bazProvider,
			@Bar Provider<Foo> fooBarProvider, @Baz Provider<Foo> fooBazProvider) {
		this.barProvider = barProvider;
		this.bazProvider = bazProvider;
		this.fooBarProvider = fooBarProvider;
		this.fooBazProvider = fooBazProvider;
	}

	@Override
	public Foo create(boolean isBar, boolean useAnnotation) {
		Foo retval = null;
		if (useAnnotation) { // demonstrate explicit Annotation binding
			if (isBar) {
				retval = fooBarProvider.get();
			} else {
				retval = fooBazProvider.get();
			}
		} else { // demonstrate implicit JIT binding
			if (isBar) {
				retval = barProvider.get();
			} else {
				retval = bazProvider.get();
			}
		}
		return retval;
	}
}
