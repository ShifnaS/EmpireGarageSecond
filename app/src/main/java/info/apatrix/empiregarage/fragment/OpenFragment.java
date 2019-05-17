package info.apatrix.empiregarage.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import info.apatrix.empiregarage.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class OpenFragment extends Fragment {


    public OpenFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root=inflater.inflate(R.layout.fragment_open, container, false);

        return root ;
    }

}
