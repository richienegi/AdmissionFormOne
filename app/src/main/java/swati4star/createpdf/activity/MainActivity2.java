package swati4star.createpdf.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

import swati4star.createpdf.R;
import swati4star.createpdf.fragment.ImageToPdfFragment;

import static swati4star.createpdf.util.Constants.OPEN_SELECT_IMAGES;

public class MainActivity2 extends AppCompatActivity
        {

static String check="1";
/*
    private SharedPreferences mSharedPreferences;
    private boolean mDoubleBackToExitPressedOnce = false;
*/
String valu="false";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main2);

        Toolbar toolbar = findViewById(R.id.toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        setSupportActionBar(toolbar);
     // suitable xml parsers for reading .docx files
        System.setProperty("org.apache.poi.javax.xml.stream.XMLInputFactory",
                "com.fasterxml.aalto.stax.InputFactoryImpl");
        System.setProperty("org.apache.poi.javax.xml.stream.XMLOutputFactory",
                "com.fasterxml.aalto.stax.OutputFactoryImpl");
        System.setProperty("org.apache.poi.javax.xml.stream.XMLEventFactory",
                "com.fasterxml.aalto.stax.EventFactoryImpl");

        // Check for app shortcuts & select default fragment
        /*Fragment fragment = checkForAppShortcutClicked();
*/
        // Check if  images are received
       /* handleReceivedImagesIntent(fragment);*/

    Intent i = getIntent();
      /*final String name=i.getStringExtra("name");
       final String con=i.getStringExtra("val");
       Log.d("myRitika",name+con);*/
      if(check.equals("1")) {
          ImageToPdfFragment fragment = new ImageToPdfFragment();
          Bundle bundle = new Bundle();
          bundle.putBoolean(OPEN_SELECT_IMAGES, true);
        /*bundle.putString("va",con);
        bundle.putString("na",name);*/

          fragment.setArguments(bundle);

          if (areImagesRecevied())
              fragment = new ImageToPdfFragment();

          getSupportFragmentManager().beginTransaction().replace(R.id.drawer_layout, fragment).commit();
          handleReceivedImagesIntent(fragment);

      /*  Button b1=findViewById(R.id.getImage);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageToPdfFragment fragment = new ImageToPdfFragment();
                Bundle bundle = new Bundle();
                bundle.putBoolean(OPEN_SELECT_IMAGES, true);

                fragment.setArguments(bundle);

                if (areImagesRecevied())
                    fragment = new ImageToPdfFragment();

                getSupportFragmentManager().beginTransaction().replace(R.id.drawer_layout,fragment).commit();
                handleReceivedImagesIntent(fragment);

            }
        });

*/
        /*mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        int count = mSharedPreferences.getInt(LAUNCH_COUNT, 0);
        *//*if (count > 0 && count % 15 == 0)
            mFeedbackUtils.rateUs();
        mSharedPreferences.edit().putInt(LAUNCH_COUNT, count + 1).apply();
*//*
        String versionName = mSharedPreferences.getString(VERSION_NAME, "");
        *//*if (!versionName.equals(BuildConfig.VERSION_NAME)) {
            WhatsNewUtils.displayDialog(this);
            mSharedPreferences.edit().putString(VERSION_NAME, BuildConfig.VERSION_NAME).apply();
        }*//*
           */
          getRuntimePermissions();
      }
    }

    /**
     * Sets a fragment based on app shortcut selected, otherwise default
     *
     * @return - instance of current fragment
     */
   /* private Fragment checkForAppShortcutClicked() {
        Fragment fragment = new HomeFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (getIntent().getAction() != null) {
            switch (Objects.requireNonNull(getIntent().getAction())) {
                case ACTION_SELECT_IMAGES:
                    fragment = new ImageToPdfFragment();
                    Bundle bundle = new Bundle();
                    bundle.putBoolean(OPEN_SELECT_IMAGES, true);
                    fragment.setArguments(bundle);
                    break;
                case ACTION_VIEW_FILES:
                    fragment = new ViewFilesFragment();
                    //setDefaultMenuSelected(1);
                    break;


                default:
                    // Set default fragment
                    fragment = new HomeFragment();
                    break;
            }
        }
        if (areImagesRecevied())
            fragment = new ImageToPdfFragment();

        fragmentManager.beginTransaction().replace(R.id.content, fragment).commit();

        return fragment;
    }
*/

    /**
     * Ininitializes default values
     */
   /* private void initializeValues() {
        mFeedbackUtils = new FeedbackUtils(this);
        mNavigationView = findViewById(R.id.nav_view);
        setDefaultMenuSelected(0);
    }*/

    /*
     * This will set default menu item selected at the position mentioned
     */
/*    public void setDefaultMenuSelected(int position) {
        if (mNavigationView != null && mNavigationView.getMenu() != null &&
                position < mNavigationView.getMenu().size()
                && mNavigationView.getMenu().getItem(position) != null) {
            mNavigationView.getMenu().getItem(position).setChecked(true);
        }
    }*/

    /**
     * Checks if images are received in the intent
     *
     * @param fragment - instance of current fragment
     */
    private void handleReceivedImagesIntent(Fragment fragment) {
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (type == null || !type.startsWith("image/"))
            return;

        if (Intent.ACTION_SEND_MULTIPLE.equals(action)) {
            handleSendMultipleImages(intent, fragment); // Handle multiple images
        } else if (Intent.ACTION_SEND.equals(action)) {
            handleSendImage(intent, fragment); // Handle single image
        }
    }


    private boolean areImagesRecevied() {
        Intent intent = getIntent();
        String type = intent.getType();
        return type != null && type.startsWith("image/");
    }

    /**
     * Get image uri from intent and send the image to homeFragment
     *
     * @param intent   - intent containing image uris
     * @param fragment - instance of homeFragment
     */
    private void handleSendImage(Intent intent, Fragment fragment) {
        Uri uri = intent.getParcelableExtra(Intent.EXTRA_STREAM);
        ArrayList<Uri> imageUris = new ArrayList<>();
        imageUris.add(uri);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(getString(R.string.bundleKey), imageUris);
        fragment.setArguments(bundle);
    }

    /**
     * Get ArrayList of image uris from intent and send the image to homeFragment
     *
     * @param intent   - intent containing image uris
     * @param fragment - instance of homeFragment
     */
    private void handleSendMultipleImages(Intent intent, Fragment fragment) {
        ArrayList<Uri> imageUris = intent.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
        if (imageUris != null) {
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(getString(R.string.bundleKey), imageUris);
            fragment.setArguments(bundle);
        }
    }

    /*@Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Fragment currentFragment = getSupportFragmentManager()
                    .findFragmentById(R.id.content);
            if (currentFragment instanceof HomeFragment) {
                checkDoubleBackPress();
            } else {
                Fragment fragment = new HomeFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.content, fragment).commit();
       //         setDefaultMenuSelected(0);
            }
        }
        */


    /**
     * Closes the app only when double clicked
     */
    /*private void checkDoubleBackPress() {
        if (mDoubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.mDoubleBackToExitPressedOnce = true;
        Toast.makeText(this, R.string.confirm_exit_message, Toast.LENGTH_SHORT).show();
    }
*/
    /*@Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        Fragment fragment = null;
        FragmentManager fragmentManager = getSupportFragmentManager();
        Bundle bundle = new Bundle();

        switch (item.getItemId()) {
            case R.id.nav_home:
                fragment = new HomeFragment();
                break;
            case R.id.nav_camera:
                fragment = new ImageToPdfFragment();
                break;
            case R.id.nav_qrcode:
                fragment = new QrBarcodeScanFragment();
                break;
            case R.id.nav_gallery:
                fragment = new ViewFilesFragment();
                break;
            case R.id.nav_merge:
                fragment = new MergeFilesFragment();
                break;
            case R.id.nav_split:
                fragment = new SplitFilesFragment();
                break;
            case R.id.nav_text_to_pdf:
                fragment = new TextToPdfFragment();
                break;
            case R.id.nav_history:
                fragment = new HistoryFragment();
                break;
            case R.id.nav_add_password:
                fragment = new RemovePagesFragment();
                bundle.putString(BUNDLE_DATA, ADD_PWD);
                fragment.setArguments(bundle);
                break;
            case R.id.nav_remove_password:
                fragment = new RemovePagesFragment();
                bundle.putString(BUNDLE_DATA, REMOVE_PWd);
                fragment.setArguments(bundle);
                break;
            case R.id.nav_share:
                mFeedbackUtils.shareApplication();
                break;
            case R.id.nav_about:
                fragment = new AboutUsFragment();
                break;
            case R.id.nav_settings:
                fragment = new SettingsFragment();
                break;
            case R.id.nav_extract_images:
                fragment = new ExtractImagesFragment();
                break;
            case R.id.nav_pdf_to_images:
                fragment = new PdfToImageFragment();
                break;
            case R.id.nav_remove_pages:
                fragment = new RemovePagesFragment();
                bundle.putString(BUNDLE_DATA, REMOVE_PAGES);
                fragment.setArguments(bundle);
                break;
            case R.id.nav_rearrange_pages:
                fragment = new RemovePagesFragment();
                bundle.putString(BUNDLE_DATA, REORDER_PAGES);
                fragment.setArguments(bundle);
                break;
            case R.id.nav_compress_pdf:
                fragment = new RemovePagesFragment();
                bundle.putString(BUNDLE_DATA, COMPRESS_PDF);
                fragment.setArguments(bundle);
                break;

        }*/
/*
        try {
            if (fragment != null)
                fragmentManager.beginTransaction().replace(R.id.content, fragment).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }*/

/*
    public void setNavigationViewSelection(int index) {
        mNavigationView.getMenu().getItem(index).setChecked(true);
    }
*/

    private boolean getRuntimePermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if ((ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) ||
                    (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) ||
                    (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED)) {
                requestPermissions(new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA},
                        0);
                return false;
            }
        }
        return true;
    }
}