package ig.intellicast.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.media.MediaControlIntent;
import android.support.v7.media.MediaRouteSelector;
import android.support.v7.media.MediaRouter;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ig.intellicast.R;
import ig.intellicast.adapter.CustomImageListAdapter;
import ig.intellicast.cloud.MainCloudActivity;
import ig.intellicast.utils.Utils;

/**
 * Created by Simranjit Kour on 9/1/15.
 */
public class FragmentImages extends Fragment {

    private MediaRouter router;
    private ArrayAdapter<RouteInfoWrapper> adapter;
    private MediaRouter.RouteInfo route;
    private Uri dataUri;
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_images, container, false);
        initializeImageLayout(Utils.IMAGE_URL_LIST);
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (router != null) {
            Toast.makeText(getActivity(), "images distroy", Toast.LENGTH_SHORT).show();
            router.removeCallback(callback);
            router.getDefaultRoute().select();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    private void initializeImageLayout(final ArrayList<String> URL_LIST) {
        final ArrayList image_details = URL_LIST;
        final GridView gridView = (GridView) rootView.findViewById(R.id.custom_list_images);
        gridView.setAdapter(new CustomImageListAdapter(getActivity(), image_details));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dataUri = Uri.parse(URL_LIST.get(position));
                castData(dataUri);
            }
        });


        rootView.findViewById(R.id.button_cast_images).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUrlCustomDialog();
            }
        });

        rootView.findViewById(R.id.button_upload_images).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MainCloudActivity.class));
            }
        });
    }

    private void showUrlCustomDialog() {
        final Dialog dialog = new Dialog(getActivity(), R.style.popup_theme);//new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_custom_url);

        final EditText editText = (EditText) dialog.findViewById(R.id.edittext_enter_url);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void afterTextChanged(Editable edt) {
                dialog.findViewById(R.id.textview_error_url).setVisibility(View.INVISIBLE);
            }
        });


        Button dialogButtonCancel = (Button) dialog.findViewById(R.id.button_cancel);
        dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Button dialogButtonCast = (Button) dialog.findViewById(R.id.button_cast);
        dialogButtonCast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editText.getText().toString().isEmpty()) {
                    String data = editText.getText().toString();
                    if ((data.startsWith("http://") || data.startsWith("https://")) && (data.endsWith(".png") || data.endsWith(".jpg") || data.endsWith(".jpeg"))) {
                        dataUri = Uri.parse(data);
                        castData(dataUri);
                        dialog.dismiss();
                    } else {
                        dialog.findViewById(R.id.textview_error_url).setVisibility(View.VISIBLE);
                    }
                } else {
//                    dataUri = Uri.parse("http://storage.googleapis.com/saliltestbucket/saliltestimage.png");
//                    dataUri = Uri.parse("http://storage.googleapis.com/intellicastbucket/IMG_20150108_184753_1206009178.jpg");
                    dataUri = Uri.parse("http://davy.preuveneers.be/phoneme/public/icon/android.png");
                    castData(dataUri);
                    dialog.dismiss();
                }

            }
        });
        dialog.show();
    }


    private void castData(Uri imageUri) {
        if (route != null) {
            if (imageUri.toString().contains(".jpg") || imageUri.toString().contains(".jpeg") || imageUri.toString().contains(".png")) {
                Toast.makeText(getActivity(), "Image Cast", Toast.LENGTH_SHORT).show();
                route.sendControlRequest(new Intent(MediaControlIntent.ACTION_PLAY).setDataAndType(imageUri, "image/*"), new MediaRouter.ControlRequestCallback() {
                });
            } else if (imageUri.toString().contains(".mp4")) {
                Toast.makeText(getActivity(), "Video Cast", Toast.LENGTH_SHORT).show();
                route.sendControlRequest(new Intent(MediaControlIntent.ACTION_PLAY).setDataAndType(imageUri, "video/*"), new MediaRouter.ControlRequestCallback() {
                });
            } else {
                Toast.makeText(getActivity(), "Invalid Url", Toast.LENGTH_SHORT).show();
            }
        } else {
            showDevicesListDialog();
        }
    }

    private void showDevicesListDialog() {
        LayoutInflater li = LayoutInflater.from(getActivity());
        View promptsView = li.inflate(R.layout.dialog_devices_list, null);

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                getActivity());

        alertDialogBuilder.setView(promptsView);

        alertDialogBuilder
                .setTitle("Select Cast Device")
                .setCancelable(false)
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

        final ListView list = (ListView) promptsView
                .findViewById(R.id.list);
        adapter = new ArrayAdapter<RouteInfoWrapper>(getActivity(), R.layout.list_item, R.id.listview_textItem);

        router = MediaRouter.getInstance(getActivity());

        for (MediaRouter.RouteInfo route : router.getRoutes()) {
            if (route.isDefault())
                continue;
            adapter.add(new RouteInfoWrapper(route));
        }

        MediaRouteSelector selector = new MediaRouteSelector.Builder().addControlCategory(Utils.castID).build();
        router.addCallback(selector, callback, MediaRouter.CALLBACK_FLAG_PERFORM_ACTIVE_SCAN);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                route = adapter.getItem(position).routeInfo;
                route.select();
                castData(dataUri);
                alertDialog.cancel();
            }
        });

        TextView emptyText = (TextView) rootView.findViewById(android.R.id.empty);
        list.setEmptyView(emptyText);
    }

    private static class RouteInfoWrapper {
        MediaRouter.RouteInfo routeInfo;

        public RouteInfoWrapper(MediaRouter.RouteInfo routeInfo) {
            this.routeInfo = routeInfo;
        }

        @Override
        public String toString() {
            return routeInfo.getName();
        }
    }

    MediaRouter.Callback callback = new MediaRouter.Callback() {
        @Override
        public void onRouteAdded(MediaRouter router, MediaRouter.RouteInfo route) {
            super.onRouteAdded(router, route);
            if (route.isDefault())
                return;
            adapter.add(new RouteInfoWrapper(route));
        }
    };
}

