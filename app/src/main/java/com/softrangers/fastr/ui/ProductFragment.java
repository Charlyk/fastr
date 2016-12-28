package com.softrangers.fastr.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;

import com.softrangers.fastr.R;
import com.softrangers.fastr.util.BarcodeScanner;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {

    private static final int SCAN_RC = 1727;
    private Unbinder mUnbinder;
    private SubmitActivity mActivity;

    @BindView(R.id.productDescriptionBtn) Button mDescriptionBtn;
    @BindView(R.id.rejectReasonBtn) Button mRejectReasonBtn;
    @BindView(R.id.invoiceIdInput) EditText mInvoiceIdInput;
    @BindView(R.id.configurationButton) Button mConfigBtn;
    @BindView(R.id.productImageView) ImageView mProductImage;
    @BindView(R.id.productBrandInput) EditText mBrandInput;
    @BindView(R.id.unitSizeInput) EditText mSizeInput;
    @BindView(R.id.unitAgeInput) EditText mAgeInput;
    @BindView(R.id.damageSwitch) SwitchCompat mDamageSwitch;

    public ProductFragment() {
        // Required empty public constructor
    }

    public static ProductFragment newInstance() {
        Bundle args = new Bundle();
        ProductFragment fragment = new ProductFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        mDamageSwitch.setOnCheckedChangeListener(this);
        return view;
    }

    @OnClick(R.id.productDescriptionBtn)
    void selectProductDescription() {

    }

    @OnClick(R.id.rejectReasonBtn)
    void selectRejectReason() {

    }

    @OnClick(R.id.scanBarcodeBtn)
    void scanBarcode() {
        Intent intent = new Intent(mActivity, BarcodeScanner.class);
        startActivityForResult(intent, SCAN_RC);
    }

    @OnClick(R.id.productImageView)
    void uploadProductImage() {

    }

    @OnClick(R.id.configurationButton)
    void selectProductConfiguration() {

    }

    @OnClick(R.id.addFieldsBtn)
    void addNewFieldsBtn() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == SubmitActivity.RESULT_OK) {
            switch (requestCode) {
                case SCAN_RC:
                    String barcode = data.getExtras().getString("code");
                    mInvoiceIdInput.setText(barcode);
                    break;
            }
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

    }
}
