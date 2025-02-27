package pl.magzik.dotoi.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.web.WebView;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.magzik.dotoi.controller.base.Controller;
import pl.magzik.dotoi.manager.data.DataEvent;
import pl.magzik.dotoi.model.Task;
import pl.magzik.dotoi.util.ApplicationUtils;
import pl.magzik.dotoi.util.MarkdownUtils;
import pl.magzik.dotoi.view.list.ApplicationListCell;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class TaskController extends Controller {

    private static final Logger log = LoggerFactory.getLogger(TaskController.class);

    private Task task;

    public void setTask(Task task) {
        log.debug("Task {} has been attached to this instance.", task);
        this.task = task;
    }

    @FXML
    private TextField titleTextField;

    @FXML
    private TextField descriptionTextField;

    @FXML
    private CheckBox deadlineCheckbox;

    @FXML
    private TextField deadlineTextField;

    @FXML
    private CheckBox recurrenceRuleCheckbox;

    @FXML
    private TextField recurrenceRuleTextField;

    @FXML
    private TextArea contentTextArea;

    @FXML
    private WebView contentWebView;

    @FXML
    private ListView<File> applicationsListView;

    @FXML
    private Label uuidLabel;

    @FXML
    private Label creationDateLabel;

    @FXML
    private void initialize() {
        applicationsListView.setCellFactory(param -> new ApplicationListCell());

        // TODO: Temporary
        applicationsListView.setItems(FXCollections.observableList(List.of(new File("/Users/maksik997/Applications/PictureComparerFX.app"))));
    }

    /**
     * Converts the contents of {@link TaskController#contentTextArea} from Markdown to HTML
     * and displays the result in {@link TaskController#contentWebView}.
     * <p>
     * This method retrieves the text from the {@code contentTextArea},
     * processes it using {@link pl.magzik.dotoi.util.MarkdownUtils},
     * and updates the {@code contentWebView} with the generated HTML content.
     * </p>
     *
     * @see pl.magzik.dotoi.util.MarkdownUtils
     * @see javafx.scene.web.WebView
     * @see javafx.scene.control.TextArea
     */
    @FXML
    public void handlePreview() {
        String content = contentTextArea.getText();
        String html = MarkdownUtils.convertToHtml(content);
        contentWebView.getEngine().loadContent(html);
    }

    @FXML
    public void handleDateSelection() {
        /* TODO:
         *   Open a dialog with date selection.
         * */
    }

    @FXML
    public void handleRecurrenceRuleSelection() {
        /* TODO:
         *   Open a dialog with recurrence rule building
         * */
    }

    @FXML
    public void handleSave() {
        /* TODO:
        *    Emit a signal with TaskAdded/TaskUpdated, depending on the state of field task.
        * */
    }

    @FXML
    public void handleAddApplication() {
        /* TODO:
        *   Add application entry to a listview.
        * */
        /* TODO: Temporary v */
        File app = ApplicationUtils.chooseApplication(getStage());
        log.debug("User choose application: {}", app);
        if (app == null) return;
        log.debug("Trying to execute: {}", app);
        try {
            ApplicationUtils.openApplication(app);
        } catch (IOException e) {
            log.error("Cannot open app.", e);
        }
    }

    @FXML
    public void handleRemoveApplication() {
        /* TODO:
        *   Remove selected application from list view.
        * */
    }

    @Override
    public void onDataUpdate(@NotNull DataEvent event) {

    }
}
