package net.terullet.bibliothekarios.arachne.javafx;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.function.Consumer;

public class Command<T> {
	private final BooleanProperty canExecuteProperty;
	public BooleanProperty canExecuteProperty() {
		return this.canExecuteProperty;
	}
	private final Consumer<T> consumer;

	public Command(Consumer<T> consumer) {
		this(true, consumer);
	}
	public Command(boolean initialValue, Consumer<T> consumer) {
		this.canExecuteProperty = new SimpleBooleanProperty(this, "canExecute", initialValue);
		this.consumer = consumer;
	}

	public void execute(T parameter) {
		this.consumer.accept(parameter);
	}
}
