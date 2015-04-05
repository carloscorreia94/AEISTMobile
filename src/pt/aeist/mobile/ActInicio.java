package pt.aeist.mobile;



import pt.aeist.mobile.res.AppController;
import pt.aeist.mobile.res.TabsPagerAdapter;
import pt.aeist.mobile.servicos.DespFrag;
import pt.aeist.mobile.servicos.SFFrag;
import pt.aeist.mobile.servicos.ServFrag;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.view.ViewPager;


public class ActInicio extends ActionBarActivity implements
		ActionBar.TabListener {
	
	private ViewPager viewPager;
	private TabsPagerAdapter mAdapter;
	private ActionBar actionBar;
	private String[] tabs = { "Eventos", "Serviços", "A AEIST" };
	private boolean _networkStatusOn;
	private boolean _connectivityChecked;
	
	
	
	public void setNetworkStatus(boolean networkStatusOn) {
		_networkStatusOn = networkStatusOn;
	}
	
	public boolean getNetworkStatus() {
		return _networkStatusOn;
	}
	
	@Override
	public void onBackPressed() {
        if(viewPager.getCurrentItem() == 1) {
            if (mAdapter.getItem(1) instanceof SFFrag) {
                ((SFFrag) mAdapter.getItem(1)).backPressed();
            }
            else if (mAdapter.getItem(1) instanceof DespFrag) {
                ((DespFrag) mAdapter.getItem(1)).backPressed();
            }
            else {
                finish();
            }
        }
        else {
            finish();
        }
    }
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		set_connectivityChecked(false);
		setContentView(R.layout.activity_act_inicio);
		//inicializacao
		viewPager = (ViewPager) findViewById(R.id.pager);
		actionBar = getSupportActionBar();
		
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		getSupportActionBar().setLogo(R.drawable.ic_launcher);
		getSupportActionBar().setDisplayUseLogoEnabled(true);
		
		mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
		viewPager.setAdapter(mAdapter);
		actionBar.setHomeButtonEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		//Adicionar Tabs
		for(String tab_name : tabs) {
			actionBar.addTab(actionBar.newTab().setText(tab_name)
					.setTabListener(this));
		}
		 viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			 
	            @Override
	            public void onPageSelected(int position) {
	                // on changing the page
	                // make respected tab selected
	                actionBar.setSelectedNavigationItem(position);
	            }
	 
	            @Override
	            public void onPageScrolled(int arg0, float arg1, int arg2) {
	            }
	 
	            @Override
	            public void onPageScrollStateChanged(int arg0) {
	            }
	        });     
	}
		 
	 
	    @Override
	    public void onTabReselected(Tab tab, FragmentTransaction ft) {
	    }
	 
	    @Override
	    public void onTabSelected(Tab tab, FragmentTransaction ft) {
	        // on tab selected
	        // show respected fragment view
	        viewPager.setCurrentItem(tab.getPosition());
	    }
	 
	    @Override
	    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	    }

	    protected void onResume()
	    {
	       super.onResume();
	       while(!is_connectivityChecked()) {}
	       AppController.getInstance().networkStatus(getBaseContext(),this);
	    	   if(!getNetworkStatus()) {
	    		   AppController.getInstance().openDialog(ActInicio.this);
	    	   }
	           	
	    }
	       
	    
	    public ViewPager getPager() {
	    	return viewPager;
	    }

		public boolean is_connectivityChecked() {
			return _connectivityChecked;
		}

		public void set_connectivityChecked(boolean _connectivityChecked) {
			this._connectivityChecked = _connectivityChecked;
		}

}
