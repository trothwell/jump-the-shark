package com.jts.guice.factory.example;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class Main {
	public static void main(String... args) {
		Injector injector = Guice.createInjector(new FooModule());
		FooFactory factory = injector.getInstance(FooFactory.class);

		Foo barInstance = factory.create(true, false);
		assertEquals(FooBar.class, barInstance.getClass());
		System.out.format("BarInstance = %s%n", barInstance.getClass()
				.getSimpleName());

		Foo bazInstance = factory.create(false, false);
		assertEquals(FooBaz.class, bazInstance.getClass());
		System.out.format("BazInstance = %s%n", bazInstance.getClass()
				.getSimpleName());

		Foo annotatedBarInstance = factory.create(true, true);
		assertEquals(FooBar.class, annotatedBarInstance.getClass());
		System.out.format("AnnotatedBarInstance = %s%n", annotatedBarInstance
				.getClass().getSimpleName());

		Foo annotatedBazInstance = factory.create(false, true);
		assertEquals(FooBaz.class, annotatedBazInstance.getClass());
		System.out.format("AnnotatedBazInstance = %s%n", annotatedBazInstance
				.getClass().getSimpleName());
	}

	private static void assertEquals(Object o1, Object o2) {
		if (o1 == null) {
			if (o2 == null) {
				return;
			} else {
				throw new AssertionError();
			}
		}
		if (!o1.equals(o2)) {
			throw new AssertionError();
		}
	}
}
