package mobi.liason.sample.content.viewmodel;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.List;

import mobi.liason.mvvm.database.Column;
import mobi.liason.mvvm.database.ViewModel;
import mobi.liason.mvvm.database.ViewModelColumn;
import mobi.liason.mvvm.providers.Path;
import mobi.liason.sample.content.models.TaskStateTable;
import mobi.liason.sample.services.TaskService;
import mobi.liason.sample.tasks.ProductTask;

/**
 * Created by Emir Hasanbegovic on 12/05/14.
 */
public class ProductTaskStateViewModel extends ViewModel {

    public static final String VIEW_NAME = "TaskStateView";
    private static final String SELECTION = TaskStateTable.TABLE_NAME;

    @Override
    public String getName(final Context context) {
        return VIEW_NAME;
    }

    @Override
    public List<Column> getColumns(Context context) {
        return Arrays.asList(Columns.COLUMNS);
    }

    @Override
    protected String getSelection(Context context) {
        return SELECTION;
    }

    @Override
    public List<Path> getPaths(Context context) {
        return Lists.newArrayList(Paths.PRODUCT_TASK_STATE);
    }

    @Override
    public Cursor query(Context context, SQLiteDatabase sqLiteDatabase, Path path, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        final String uriString = uri.toString();

        final String overridenSelection = Columns.URI.getName() + "=?";
        final String[] overridenSelectionArguments = {uriString};

        final Cursor cursor = super.query(context, sqLiteDatabase, path, uri, projection, overridenSelection, overridenSelectionArguments, sortOrder);

        TaskService.startTask(context, uri);

        return cursor;
    }

    public static class Columns {
        public static final ViewModelColumn URI = new ViewModelColumn(VIEW_NAME, TaskStateTable.Columns.URI);
        public static final ViewModelColumn STATE = new ViewModelColumn(VIEW_NAME, TaskStateTable.Columns.STATE);
        public static final ViewModelColumn TIME = new ViewModelColumn(VIEW_NAME, TaskStateTable.Columns.TIME);
        public static final ViewModelColumn JSON = new ViewModelColumn(VIEW_NAME, TaskStateTable.Columns.JSON);
        public static final Column[] COLUMNS = new Column[]{URI, STATE, TIME, JSON};
    }

    public static class Paths {
        public static final Path PRODUCT_TASK_STATE = ProductTask.Paths.PRODUCTS;
    }

}
