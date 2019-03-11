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
		
	}
	
	@Override
	public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
		if(firstItem.equals(String.valueOf(list.getSelectedItem()))){
			// ToDo when first item is selected
//			Toast.makeText(view.getContext(), "First item is "+list.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
			selectedItem = list.getSelectedItem().toString();
		} else {
//			Toast.makeText(view.getContext(),
//					"You have selected : " + list.getItemAtPosition(i).toString(),
//					Toast.LENGTH_LONG).show();
			selectedItem = list.getItemAtPosition(i).toString();
			// Todo when item is selected by the user
		}
		
	}
	
	@Override
	public void onNothingSelected(AdapterView<?> adapterView) {
	
	}
	
	public String getSelectedItem() { return selectedItem; }
}
