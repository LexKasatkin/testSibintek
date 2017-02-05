package lex.test.sibintek;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListFragment extends  android.support.v4.app.Fragment{

    RecyclerView mRecyclerView ;
    String jsonString;
    Adapter mAdapter=null;
    protected Handler handler;
    private String urlLoad;
    int counterOfLoading=1;
    ListUser listUser;
    private LinearLayoutManager mLayoutManager;

    private OnFragmentInteractionListener mListener;
    SwipeRefreshLayout mSwipeRefreshLayout;

    public ListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListFragment newInstance(String param1, String param2) {
        ListFragment fragment = new ListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_list, container, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.recyclerview);
        mSwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LoadData();
            }
        });
        LoadData();
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


    public class LoadItemsTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            if(counterOfLoading==1){
                mSwipeRefreshLayout.setRefreshing(true);
            }
            urlLoad = "https://api.github.com/users/whatever/followers?page=" + String.valueOf(counterOfLoading) + "&per_page=5";
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... urls) {
                HTTPrequest httPrequest = new HTTPrequest(urlLoad, getContext());
                jsonString = httPrequest.getJSONString();
                return jsonString;
        }

        @Override
        protected void onPostExecute(final String jsonString) {
            listUser.setUsersFromJSONString(jsonString);
                if (counterOfLoading == 1) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
                if (listUser.userArrayList.size() != 0) {
                    if (counterOfLoading > 1) {
                    listUser.userArrayList.remove(listUser.userArrayList.size() - 1);
                        mAdapter.notifyItemRemoved(listUser.userArrayList.size() );
                    }
                    for (int i = 0; i < listUser.userArrayList.size(); i++) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                mAdapter.notifyItemInserted(listUser.userArrayList.size());

                            }
                        });
                    }

                    mAdapter.notifyItemRemoved(listUser.userArrayList.size() -1);

                    mAdapter.notifyDataSetChanged();
                    mAdapter.setLoaded();
                    counterOfLoading++;
            }
        }
    }

    public void LoadData(){
       counterOfLoading=1;
        handler = new Handler();
        listUser = new ListUser();
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new Adapter(listUser.userArrayList, mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
        if(NetworkManager.isNetworkAvailable(getContext())){
            LoadItemsTask loadItemsTask=new LoadItemsTask();
            loadItemsTask.execute();
        }else{
            Toast toast = Toast.makeText(getActivity(), "Проверьте соединение"
                    , Toast.LENGTH_SHORT);
            toast.show();
        }
        mAdapter.setOnLoadMoreListener(new Adapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                listUser.userArrayList.add(null);
                mAdapter.notifyItemInserted(listUser.userArrayList.size() - 1);
                if(NetworkManager.isNetworkAvailable(getContext())){
                    LoadItemsTask loadItemsTask=new LoadItemsTask();
                    loadItemsTask.execute();
                } else{
                    Toast toast = Toast.makeText(getActivity() ,"Проверьте соединение"
                            , Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }

}
