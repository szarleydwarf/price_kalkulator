package eu.rjch.kalkulatorcen.utilities;


import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

public class ItemSelectedListener implements AdapterView.OnItemSelectedListener {
	private final Spinner list;
	String firstItem, selectedItem;
	
	public ItemSelectedListener(Spinner sp) {
		this.list = sp;
		// ToDo figure out what wrong with the selection
		this.list.setSelection(6);
		this.firstItem = String.valueOf(list.getSelectedItem());
		this.selectedItem = String.valueOf(list.getSelectedItem());
	}
	
	@Override
	public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
		if(firstItem.equals(String.valueOf(list.getSelectedItem()))){
			selectedItem = list.getSelectedItem().toString();
		} else {
			selectedItem = list.getItemAtPosition(i).toString();
		}
		
	}
	
	@Override
	public void onNothingSelected(AdapterView<?> adapterView) {
	
	}
	
	public String getSelectedItem() { return selectedItem; }
}
