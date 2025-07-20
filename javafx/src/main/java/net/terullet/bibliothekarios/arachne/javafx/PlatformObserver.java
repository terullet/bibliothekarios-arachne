package net.terullet.bibliothekarios.arachne.javafx;

import javafx.application.Platform;
import javafx.beans.property.Property;
import net.terullet.bibliothekarios.arachne.core.presentation.ObservableList;
import net.terullet.bibliothekarios.arachne.core.presentation.Observer;

import java.util.function.Function;

public class PlatformObserver {
	public static <T> Observer<T> wrap(Property<T> property) {
		return (newValue) -> Platform.runLater(() -> property.setValue(newValue));
	}
	public static <T,U> ObservableList.Observer<T> wrap(javafx.collections.ObservableList<U> observableList, Function<T, U> converter) {
		return (listChange) -> {
			switch (listChange) {
				case ObservableList.ListClearing<T> lc:
					Platform.runLater(observableList::clear);
					break;
				case ObservableList.ListSort<T> ls:
					Platform.runLater(() -> {
						observableList.clear();
						observableList.addAll(ls.getSorted().stream().map(converter).toList());
					});
					break;
				case ObservableList.ListInsertions<T> li:
					Platform.runLater(() -> {
						for (ObservableList.InsertionElement<T> ie : li.getInsertions()) {
							observableList.add(ie.getIndex(), converter.apply(ie.getItem()));
						}
					});
					break;
				case ObservableList.ListRemovals<T> lr:
					Platform.runLater(() -> {
						for (ObservableList.RemovalElement<T> re : lr.getRemovals()) {
							observableList.remove(re.getIndex());
						}
					});
					break;
				case ObservableList.ListReplacements<T> lrp:
					Platform.runLater(() -> {
						for (ObservableList.ReplacementElement<T> rpe : lrp.getReplacements()) {
							observableList.set(rpe.getIndex(), converter.apply(rpe.getItem()));
						}
					});
					break;
			}
		};
	}
}
