package lex.test.sibintek;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

public class MainActivity extends SingleFragmentActivity
    {
        @Override
        protected Fragment createFragment() {
        return new ListFragment();
    }

        public void switchContent(int id, Fragment fragment) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(id, fragment, fragment.toString());
            ft.addToBackStack(null);
            ft.commit();
        }
}
