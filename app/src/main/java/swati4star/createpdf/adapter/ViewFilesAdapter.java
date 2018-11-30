package swati4star.createpdf.adapter;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import swati4star.createpdf.R;
import swati4star.createpdf.database.DatabaseHelper;
import swati4star.createpdf.interfaces.DataSetChanged;
import swati4star.createpdf.interfaces.EmptyStateChangeListener;
import swati4star.createpdf.interfaces.ItemSelectedListener;
import swati4star.createpdf.util.DirectoryUtils;
import swati4star.createpdf.util.FileUtils;
import swati4star.createpdf.util.PopulateList;

import static swati4star.createpdf.util.Constants.SORTING_INDEX;
import static swati4star.createpdf.util.FileUtils.getFormattedDate;
import static swati4star.createpdf.util.StringUtils.showSnackbar;

/**
 * Created by swati on 9/10/15.
 * <p>
 * An adapter to view the existing PDF files
 */

public class ViewFilesAdapter extends RecyclerView.Adapter<ViewFilesAdapter.ViewFilesHolder>
        implements DataSetChanged, EmptyStateChangeListener {

    private final Activity mActivity;
    private final EmptyStateChangeListener mEmptyStateChangeListener;
    private final ItemSelectedListener mItemSelectedListener;

    private ArrayList<File> mFileList;
    private final ArrayList<Integer> mSelectedFiles;

    private final FileUtils mFileUtils;
    private final DatabaseHelper mDatabaseHelper;
    private final SharedPreferences mSharedPreferences;

    /**
     * Returns adapter instance
     *
     * @param activity                 the activity calling this adapter
     * @param feedItems                array list containing path of files
     * @param emptyStateChangeListener interface for empty state change
     * @param itemSelectedListener     interface for monitoring the status of file selection
     */
    public ViewFilesAdapter(Activity activity,
                            ArrayList<File> feedItems,
                            EmptyStateChangeListener emptyStateChangeListener,
                            ItemSelectedListener itemSelectedListener) {
        this.mActivity = activity;
        this.mEmptyStateChangeListener = emptyStateChangeListener;
        this.mItemSelectedListener = itemSelectedListener;
        this.mFileList = feedItems;
        mSelectedFiles = new ArrayList<>();
        mFileUtils = new FileUtils(activity);

        mDatabaseHelper = new DatabaseHelper(mActivity);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(mActivity);
    }

    @NonNull
    @Override
    public ViewFilesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_file, parent, false);
        return new ViewFilesHolder(itemView, mItemSelectedListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewFilesHolder holder, final int pos) {
        final int position = holder.getAdapterPosition();
        final File file = mFileList.get(position);

        holder.mFilename.setText(file.getName());
        holder.mFilesize.setText(FileUtils.getFormattedSize(file));
        holder.mFiledate.setText(getFormattedDate(file));
        holder.checkBox.setChecked(mSelectedFiles.contains(position));
        holder.mRipple.setOnClickListener(view -> {

        });
    }


    @Override
    public int getItemCount() {
        return mFileList == null ? 0 : mFileList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Sets pdf files
     *
     * @param pdfFiles array list containing path of files
     */
    public void setData(ArrayList<File> pdfFiles) {
        mFileList = pdfFiles;
        notifyDataSetChanged();
    }


    @Override
    public void updateDataset() {
   /*     int index = mSharedPreferences.getInt(SORTING_INDEX, NAME_INDEX);
        new PopulateList(this, this,
                new DirectoryUtils(mActivity), index).execute();
  */  }

    @Override
    public void setEmptyStateVisible() {

    }

    @Override
    public void setEmptyStateInvisible() {

    }

    @Override
    public void showNoPermissionsView() {

    }

    @Override
    public void hideNoPermissionsView() {

    }

    public class ViewFilesHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.fileRipple)
        MaterialRippleLayout mRipple;
        @BindView(R.id.fileName)
        TextView mFilename;
        @BindView(R.id.checkbox)
        CheckBox checkBox;
        @BindView(R.id.fileDate)
        TextView mFiledate;
        @BindView(R.id.fileSize)
        TextView mFilesize;
        @BindView(R.id.encryptionImage)
        ImageView mEncryptionImage;

        ViewFilesHolder(View itemView, ItemSelectedListener itemSelectedListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    if (!mSelectedFiles.contains(getAdapterPosition())) {
                        mSelectedFiles.add(getAdapterPosition());
                        itemSelectedListener.isSelected(true, mSelectedFiles.size());
                    }
                } else
                    mSelectedFiles.remove(Integer.valueOf(getAdapterPosition()));
                itemSelectedListener.isSelected(false, mSelectedFiles.size());
            });
        }
    }
}