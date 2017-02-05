package lex.test.sibintek;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UserFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserFragment extends android.support.v4.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    String jsonString;
    ImageView imageView;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private String login;
    TextView tvUser;
    private ProgressDialog progressDialog;

    public UserFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserFragment newInstance(String param1, String param2) {
        UserFragment fragment = new UserFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            login=getArguments().getString("login");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_user, container, false);
        imageView = (ImageView) v.findViewById(R.id.ivUser);
        tvUser=(TextView)v.findViewById(R.id.tvUser);
        if(NetworkManager.isNetworkAvailable(getContext())){
            LoadUserInfo loadUserInfo=new LoadUserInfo();
            loadUserInfo.execute();
        }else{
            Toast toast = Toast.makeText(getActivity(), "Проверьте соединение"
                    , Toast.LENGTH_SHORT);
            toast.show();
        }
        imageView.setImageResource(R.mipmap.ic_launcher);
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    public class LoadUserInfo extends AsyncTask<String, Void, String> {


        private String urlLoad;

        @Override
        protected void onPreExecute() {
            showProgressDialog(true);
            urlLoad = "https://api.github.com/users/"+login;
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... urls) {
            synchronized (this) {
                HTTPrequest httPrequest = new HTTPrequest(urlLoad, getContext());
                jsonString = httPrequest.getJSONString();
                return jsonString;
            }
        }

        @Override
        protected void onPostExecute(final String jsonString) {
            tvUser.setText(jsonString);
            User user=new User();
            user.setUser(jsonString);
            tvUser.setText("Логин: "+user.getLogin()
                    +"\nID: "+user.getId()
                    +"\nURL: "+user.getUrl()
                    +"\nКомпания: "+user.getCompany()
                    +"\nБлог: "+user.getBlog()
                    +"\nМестоположение: "
            +user.getLocation()+"\ne-mail: "+user.getEmail()
                    +"\nКоличество публичных репозиториев:"+user.getPublic_repos());
            if(user.getAvatar_url()!=null && !user.getAvatar_url().equals("")) {
                Picasso.with(getContext()).load(user.getAvatar_url()).into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        imageView.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });
            }
            showProgressDialog(false);
        }
    }

    public void showProgressDialog(boolean visible) {
        if (visible) {
            if (progressDialog == null || !progressDialog.isShowing()) {
                try {
                    progressDialog = new ProgressDialog(getContext(), R.style.MyTheme);
                    progressDialog.setProgress(R.drawable.circular_progress_bar);
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.setCancelable(false);
                    progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    progressDialog.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            try {
                if (progressDialog != null && progressDialog.isShowing())
                    progressDialog.dismiss();
                progressDialog = null;
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
