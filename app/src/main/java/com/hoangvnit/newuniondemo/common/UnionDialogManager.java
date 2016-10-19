package com.hoangvnit.newuniondemo.common;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.hoangvnit.newuniondemo.R;
import com.hoangvnit.newuniondemo.mvp.model.Organization;
import com.hoangvnit.newuniondemo.util.LogUtil;

import java.util.Date;

/**
 * This class manage common dialog of New Union Demo app.
 *
 * @author hoangnguyen {www.hoangvnit.com}.
 *         Created on 10/19/16.
 */

public class UnionDialogManager {

    private static UnionDialogManager unionDialogManager;

    public static UnionDialogManager shareInstance() {
        if (unionDialogManager == null) {
            unionDialogManager = new UnionDialogManager();
        }
        return unionDialogManager;
    }

    /**
     * Callback methods using for {@link UnionDialogManager#showAddOrganizationDialog(Context, boolean, int, Organization, OrganizationDialogListener)}.
     */
    public interface OrganizationDialogListener {
        void onCreate(Organization organization);

        void onUpdate(Organization organization, int position);

        void onCancel();
    }

    /**
     * This is the common method for showing the dialog for creating and updating Organization.
     *
     * @param context                      Context
     * @param isUpdate                     {@code false} if show dialog for creating.
     *                                     {@code true} if show dialog for updating.
     * @param itemPosition                 The position of organization in the list. Just use for updating.
     *                                     Value is {@code -1} for creating.
     *                                     Value is a {@code non-negative int} for updating.
     * @param organization                 This param just used for update action.
     *                                     Use this model to assign data for organization fields.
     *                                     Value is {@code null} for creating.
     *                                     Value is {@link Organization} for updating.
     * @param onOrganizationDialogListener Listener for dialog buttons's action - update/create/cancel.
     */
    public void showAddOrganizationDialog(final Context context,
                                          boolean isUpdate,
                                          final int itemPosition,
                                          final Organization organization,
                                          final OrganizationDialogListener onOrganizationDialogListener) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);


            LayoutInflater inflater = LayoutInflater.from(context);
            View dialogView = inflater.inflate(R.layout.dialog_create_organization, null);
            builder.setView(dialogView);

            final AlertDialog dialog = builder.create();
            dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

            final EditText edtName = (EditText) dialogView.findViewById(R.id.edt_organization_name);
            final EditText edtPinCode = (EditText) dialogView.findViewById(R.id.edt_organization_pincode);
            final EditText edtAddress = (EditText) dialogView.findViewById(R.id.edt_organization_address);
            final EditText edtCountry = (EditText) dialogView.findViewById(R.id.edt_organization_country);
            final EditText edtState = (EditText) dialogView.findViewById(R.id.edt_organization_state);
            final EditText editCity = (EditText) dialogView.findViewById(R.id.edt_organization_city);

            Button btnCreate = (Button) dialogView.findViewById(R.id.btn_create);
            Button btnUpdate = (Button) dialogView.findViewById(R.id.btn_update);
            Button btnCancel = (Button) dialogView.findViewById(R.id.btn_cancel);

            if (isUpdate) {
                btnCreate.setVisibility(View.GONE);
                btnUpdate.setVisibility(View.VISIBLE);

                setDataForViewFromOrganizationModel(
                        organization,
                        edtName,
                        edtPinCode,
                        edtAddress,
                        edtCountry,
                        edtState,
                        editCity);

                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Organization organization = createOrganizationModelFromView(
                                edtName,
                                edtPinCode,
                                edtAddress,
                                edtCountry,
                                edtState,
                                editCity);

                        onOrganizationDialogListener.onUpdate(organization, itemPosition);
                        dialog.dismiss();
                    }
                });

            } else {
                btnUpdate.setVisibility(View.GONE);
                btnCreate.setVisibility(View.VISIBLE);

                btnCreate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Organization organization = createOrganizationModelFromView(
                                edtName,
                                edtPinCode,
                                edtAddress,
                                edtCountry,
                                edtState,
                                editCity);

                        onOrganizationDialogListener.onCreate(organization);
                        dialog.dismiss();
                    }
                });
            }

            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onOrganizationDialogListener.onCancel();
                    dialog.dismiss();
                }
            });

            dialog.show();

        } catch (Exception e) {
            LogUtil.e("Exception: " + e.getMessage());
        }
    }

    /**
     * Get value from text input fields to create {@link Organization} object for save/update Firebase data.<br/>
     * This is used for {@link UnionDialogManager#showAddOrganizationDialog(Context, boolean, int, Organization, OrganizationDialogListener)}.
     *
     * @param edtName
     * @param edtPinCode
     * @param edtAddress
     * @param edtCountry
     * @param edtState
     * @param edtCity
     * @return {@link Organization} object created from input text field of Organization form.
     */
    private Organization createOrganizationModelFromView(EditText edtName,
                                                         EditText edtPinCode,
                                                         EditText edtAddress,
                                                         EditText edtCountry,
                                                         EditText edtState,
                                                         EditText edtCity) {
        int iPinCode = -1;
        String sPinCode = edtPinCode.getText().toString().trim();
        if (sPinCode != null && !sPinCode.equals("")) {
            try {
                iPinCode = Integer.parseInt(sPinCode);
            } catch (Exception e) {
                LogUtil.e(e.getMessage());
            }
        }

        Organization organization = new Organization(
                edtName.getText().toString().trim(),
                iPinCode,
                edtAddress.getText().toString().trim(),
                edtCountry.getText().toString().trim(),
                edtState.getText().toString().trim(),
                edtCity.getText().toString().trim()
        );

        organization.setTime(new Date());

        return organization;
    }

    /**
     * Set data for Organization form fields.
     * This is used for {@link UnionDialogManager#showAddOrganizationDialog(Context, boolean, int, Organization, OrganizationDialogListener)}.
     *
     * @param organization
     * @param edtName
     * @param edtPinCode
     * @param edtAddress
     * @param edtCountry
     * @param edtState
     * @param edtCity
     */
    private void setDataForViewFromOrganizationModel(Organization organization,
                                                     EditText edtName,
                                                     EditText edtPinCode,
                                                     EditText edtAddress,
                                                     EditText edtCountry,
                                                     EditText edtState,
                                                     EditText edtCity) {
        if (organization != null) {
            edtName.setText(organization.getOrganizationName());
            edtPinCode.setText(String.valueOf(organization.getPinCode()));
            edtAddress.setText(organization.getAddress());
            edtCountry.setText(organization.getCountry());
            edtState.setText(organization.getState());
            edtCity.setText(organization.getCity());
        }
    }
}
