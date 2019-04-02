package eu.rjch.kalkulatorcen.utilities;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import eu.rjch.kalkulatorcen.activities.TheApp;

public class ItemSelectedListener implements AdapterView.OnItemSelectedListener {
	private final Spinner list;
	private final TheApp app;
	String firstItem, selectedItem;
	
	public ItemSelectedListener(Spinner sp,TheApp a) {
		this.list = sp;
		this.list.setSelection(5);
		this.firstItem = String.valueOf(list.getSelectedItem());
		this.selectedItem = String.valueOf(list.getSelectedItem());
		app = a;
	}
	
	@Override
	public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
		if(!firstItem.equals(String.valueOf(list.getSelectedItem()))){
			app.setSeekBarValue(false);
			selectedItem = list.getSelectedItem().toString();
		} else {
			app.setSeekBarValue(false);
			selectedItem = list.getItemAtPosition(i).toString();
		}
		
	}
	
	@Override
	public void onNothingSelected(AdapterView<?> adapterView) {
	
	}
	
	public String getSelectedItem() { return selectedItem; }
}
