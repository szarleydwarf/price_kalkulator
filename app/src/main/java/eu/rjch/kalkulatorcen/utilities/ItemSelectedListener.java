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
		this.list.setSelection(5);
		this.firstItem = String.valueOf(list.getSelectedItem());
		this.selectedItem = String.valueOf(list.getSelectedItem());
	}
	
	@Override
	public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
		if(firstItem.equals(String.valueOf(list.getSelectedItem()))){
			// ToDo when first item is selected
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
