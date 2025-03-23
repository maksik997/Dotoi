package pl.magzik.dotoi.view;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import pl.magzik.dotoi.manager.TranslationManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Optional;

public class DateTimePicker {

    public static Optional<LocalDateTime> showAndWait(Stage owner, LocalDateTime initialDateTime) {
        Objects.requireNonNull(owner);

        LocalDate initialDate = initialDateTime == null ? LocalDate.now() : initialDateTime.toLocalDate();
        String initialHour = initialDateTime == null ? "00" : String.valueOf(initialDateTime.getHour());
        String initialMinute = initialDateTime == null ? "00" : String.valueOf(initialDateTime.getMinute());
        DatePicker datePicker = new DatePicker(initialDate);
        TextField hourTextField = new TextField(initialHour);
        TextField minuteTextField = new TextField(initialMinute);

        GridPane grid = createGrid(datePicker, hourTextField, minuteTextField);
        Dialog<LocalDateTime> dialog = createDialog(grid);

        dialog.setResultConverter(button -> {
            if (button != ButtonType.OK) { return null; }
            try {
                LocalDate date = datePicker.getValue();
                int hour = Integer.parseInt(hourTextField.getText());
                int minute = Integer.parseInt(minuteTextField.getText());
                if ((hour < 0 || hour > 23) && (minute < 0 || minute > 59)) {
                    throw new IllegalArgumentException("Hour must be between 0 and 23 and minute must be between 0 and 59");
                }
                return LocalDateTime.of(date, LocalTime.of(hour, minute));
            } catch (Exception e) {
                AlertUtils.showErrorAlert(owner, TranslationManager.getInstance().translate("datetimepicker.error.bad-format"));
            }
            return null;
        });
        return dialog.showAndWait();
    }

    private static GridPane createGrid(DatePicker datePicker, TextField hourTextField, TextField minuteTextField) {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label(TranslationManager.getInstance().translate("datetimepicker.label.date")), 0, 0);
        grid.add(datePicker, 1, 0);
        grid.add(new Label(TranslationManager.getInstance().translate("datetimepicker.label.hour")), 0, 1);
        grid.add(hourTextField, 1, 1);
        grid.add(new Label(TranslationManager.getInstance().translate("datetimepicker.label.minute")), 0, 2);
        grid.add(minuteTextField, 1, 2);
        return grid;
    }

    private static Dialog<LocalDateTime> createDialog(GridPane grid) {
        Dialog<LocalDateTime> dialog = new Dialog<>();
        dialog.setTitle(TranslationManager.getInstance().translate("datetimepicker.title"));
        dialog.setHeaderText(TranslationManager.getInstance().translate("datetimepicker.header"));
        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        return dialog;
    }
}
