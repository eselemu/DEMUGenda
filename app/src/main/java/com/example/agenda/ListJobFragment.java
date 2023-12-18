package com.example.agenda;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import Adapters.TaskViewAdapter;

import static Global.Info.currentUser;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListJobFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListJobFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView rv;
    View view;

    public ListJobFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListJobFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListJobFragment newInstance(String param1, String param2) {
        ListJobFragment fragment = new ListJobFragment();
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
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        int firstIndexTask;
        view = inflater.inflate(R.layout.fragment_list_job, container, false);
        rv = view.findViewById(R.id.rv_ListJob);
        currentUser.SortTasksByDate();
        firstIndexTask = currentUser.GetFirstIndexTaskPosterior();
        TaskViewAdapter taskViewAdapter = new TaskViewAdapter();
        taskViewAdapter.context = getContext();
        taskViewAdapter.fragmentManager = getActivity().getSupportFragmentManager();
        LinearLayoutManager llm = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        if(firstIndexTask != -1)
            llm.scrollToPosition(firstIndexTask);
        //llm.scrollToPosition(2);

        rv.setLayoutManager(llm);
        rv.setAdapter(taskViewAdapter);
        return view;
    }
}