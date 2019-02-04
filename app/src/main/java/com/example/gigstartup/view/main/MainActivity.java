package com.example.gigstartup.view.main;

import android.content.Context;
import android.content.Intent;
import android.databinding.Bindable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.gigstartup.R;
import com.example.gigstartup.adapters.NavigationMenuAdapter;
import com.example.gigstartup.databinding.ActivityMainBinding;
import com.example.gigstartup.utils.ActionBarUtils;
import com.example.gigstartup.utils.Constants;
import com.example.gigstartup.utils.SharedPref;
import com.example.gigstartup.utils.ToastUtils;
import com.example.gigstartup.view.account.login.LoginActivity;
import com.example.gigstartup.view.base.BaseActivity;
import com.example.gigstartup.view.feedback.FeedbackFragment;
import com.example.gigstartup.view.home.HomeFragment;
import com.example.gigstartup.view.notification.NotificationFragment;
import com.example.gigstartup.view.profile.ProfileFragment;
import com.example.gigstartup.view.work.complete.CompleteWorkFragment;
import com.example.gigstartup.view.work.denied.DeniedWorkFragment;
import com.example.gigstartup.view.work.hire.HireWorkFragment;
import com.example.gigstartup.view.work.request.RequestWorkFragment;
import com.example.gigstartup.viewModel.BaseViewModel;

public class MainActivity extends BaseActivity<BaseViewModel, ActivityMainBinding> implements NavigationMenuAdapter.NavigationMenuItemClickListener {
    private Context mContext = this;
    public ActionBarDrawerToggle toggle;

    @Override
    protected int containerId() {
        return R.id.content_home;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setViewModel(getViewModel());
        setupNavigationMenuItems();
        getSupportFragmentManager().addOnBackStackChangedListener(this::showHamburgerOrBack);
        bundle.putString("title", "Home");
        setHeaderProfileFields();
        replaceFragment(HomeFragment.class, null, true, bundle, null, 0, false);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected Class viewModelClass() {
        return BaseViewModel.class;
    }

    @Override
    protected BaseViewModel.Factory factory() {
        return new BaseViewModel.Factory() {
            @Override
            public BaseViewModel getClassInstance() {
                return new BaseViewModel();
            }
        };
    }

    private void setupNavigationMenuItems() {
        NavigationMenuAdapter navigationMenuAdapter = new NavigationMenuAdapter(mContext, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        getBinding().navigationRV.setLayoutManager(linearLayoutManager);
        getBinding().navigationRV.setAdapter(navigationMenuAdapter);
        toggle = new ActionBarDrawerToggle(this, getBinding().drawerLayout, getBinding().appBarHome.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
            }

        };
        getBinding().drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }


    private void setHeaderProfileFields() {
        if(SharedPref.read(Constants.IS_LOGIN.toString(), false)) {
            getBinding().navHeaderHome.userName.setText(SharedPref.read(Constants.NAME.toString(), ""));
            getBinding().navHeaderHome.userPhone.setText(SharedPref.read(Constants.EMAIL.toString(), ""));
            getBinding().navHeaderHome.userName.setVisibility(View.VISIBLE);
            getBinding().navHeaderHome.userPhone.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(SharedPref.read(Constants.IMAGE_URL, ""))
                    .apply(RequestOptions.placeholderOf(ContextCompat.getDrawable(this,R.drawable.ic_user))
                    .transform(new CircleCrop()).dontAnimate()).into(getBinding().navHeaderHome.profileIV);
            getBinding().navHeaderHome.loginTV.setVisibility(View.GONE);
        } else {
            getBinding().navHeaderHome.loginTV.setVisibility(View.VISIBLE);
            getBinding().navHeaderHome.userPhone.setVisibility(View.GONE);
            getBinding().navHeaderHome.userName.setVisibility(View.GONE);
            getBinding().navHeaderHome.profileIV.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_user));
        }
    }

    Bundle bundle = new Bundle();

    @Override
    public void OnMenuItemClick(int name) {
        getBinding().drawerLayout.closeDrawer(getBinding().drawer);
        switch (name) {
            case R.string.menu_home:
                bundle.putString("title", "Home");
                replaceFragment(HomeFragment.class, null, true, bundle, null, 0, false);
                break;
            case R.string.menu_requested_work:
                bundle.putString("title", "Request Work");
                replaceFragment(RequestWorkFragment.class, null, true, bundle, null, 0, false);
                break;
            case R.string.menu_hire_worker:
                bundle.putString("title", "Request Work");
                replaceFragment(HireWorkFragment.class, null, true, bundle, null, 0, false);
                break;
            case R.string.menu_completed_work:
                bundle.putString("title", "Complted Work");
                replaceFragment(CompleteWorkFragment.class, null, true, bundle, null, 0, false);
                break;
            case R.string.menu_denied_work:
                bundle.putString("title", "Denied Work");
                replaceFragment(DeniedWorkFragment.class, null, true, bundle, null, 0, false);
                break;
            case R.string.menu_profile:
                bundle.putString("title", "Profile");
                replaceFragment(ProfileFragment.class, null, true, bundle, null, 0, false);
                break;
            case R.string.menu_feedback:
                bundle.putString("title", "Feedback");
                replaceFragment(FeedbackFragment.class, null, true, bundle, null, 0, false);
                break;
            case R.string.menu_logout:
                SharedPref.clearData(this);
                startActivity(new Intent(mContext, LoginActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK));
                ActionBarUtils.finishActivity(this);
                ToastUtils.toast(mContext, getString(R.string.log_out_msg));
                break;
        }
    }

    public void showHamburgerOrBack() {
        if (getSupportActionBar() != null) {
            if (getCurrentFragment() instanceof HomeFragment) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
                toggle.setDrawerIndicatorEnabled(true);
                setStatusBarIconColor(false);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark));
                }
                toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.colorPrimaryDark));

                getBinding().drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            }
        }
    }

    public void setStatusBarIconColor(boolean show) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decor = getWindow().getDecorView();
            if (show) {
                decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {
                decor.setSystemUiVisibility(0);
            }
        }

    }

    public void onNavHeaderClick(View view) {
        OnMenuItemClick(R.string.menu_profile);
    }
    public void onNotificationClick(View view) {
        replaceFragment(NotificationFragment.class, null, true, bundle, null, 0, false);
    }

    @Override
    public void onBackPressed() {
        getBinding().drawerLayout.closeDrawer(Gravity.START);
        if (getCurrentFragment() != null && getCurrentFragment() instanceof HomeFragment) {
            // replaceFragment(HomeFragment.class, null, true, null, null, 0, false);
            ActivityCompat.finishAffinity(this);
        } else {
            replaceFragment(HomeFragment.class, null, true, null, null, 0, false);
        }
    }
}
