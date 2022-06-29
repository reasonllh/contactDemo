package util;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.util.Callback;
import javafx.util.StringConverter;

/**
 * @ClassName CellFactory
 * @Author asus
 * @Date 2022/5/2 17:14
 * @Description 工具类，方便使用CheckBoxTableCell
 * @Version 1.0.0
 */
public class CellFactory {
    public static <S, T> Callback<TableColumn<S, T>, TableCell<S, T>> tableCheckBoxColumn(
            Callback<Integer, ObservableValue<Boolean>> paramCallback) {
        return tableCheckBoxColumn(paramCallback, null);
    }

    public static <S, T> Callback<TableColumn<S, T>, TableCell<S, T>> tableCheckBoxColumn(
            final Callback<Integer, ObservableValue<Boolean>> getSelectedProperty,
            final StringConverter<T> converter) {
        return new Callback<TableColumn<S, T>, TableCell<S, T>>() {
            @Override
            public TableCell<S, T> call(TableColumn<S, T> paramTableColumn) {
                return new CheckBoxTableCell<S, T>(getSelectedProperty, converter);
            }
        };
    }
}
