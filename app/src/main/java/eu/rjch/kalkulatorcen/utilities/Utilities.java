package eu.rjch.kalkulatorcen.utilities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class Utilities {
    public Utilities() {
    }

    public void showDialog(Context ctx, final String s) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setMessage(s);

        builder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
//						boolean isOk = true;
//						if(isOk)
							return;
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
