package com.heinrichreimersoftware.ellipticcurvechiffre;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.heinrichreimersoftware.ellipticcurvechiffre.utils.EllipticCurve;
import com.heinrichreimersoftware.ellipticcurvechiffre.utils.EllipticCurveChiffreUtils;
import com.heinrichreimersoftware.ellipticcurvechiffre.utils.Point;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.inputA)
    EditText inputA;
    @Bind(R.id.inputB)
    EditText inputB;
    @Bind(R.id.inputGX)
    EditText inputGX;
    @Bind(R.id.inputGY)
    EditText inputGY;
    @Bind(R.id.inputP)
    EditText inputP;
    @Bind(R.id.inputAlpha)
    EditText inputAlpha;
    @Bind(R.id.inputPointBX)
    EditText inputPointBX;
    @Bind(R.id.inputPointBY)
    EditText inputPointBY;

    @Bind(R.id.outputN)
    TextView outputN;
    @Bind(R.id.outputPointA)
    TextView outputPointA;
    @Bind(R.id.outputPointP)
    TextView outputPointP;

    @Bind(R.id.calculateN)
    Button calculateN;
    @Bind(R.id.calculatePointA)
    Button calculatePointA;
    @Bind(R.id.calculatePointP)
    Button calculatePointP;

    EllipticCurveChiffreUtils chiffreUtils = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        calculateN.setOnClickListener(new CalculateNClickListener());
        calculatePointA.setOnClickListener(new CalculatePointAClickListener());
        calculatePointP.setOnClickListener(new CalculatePointPClickListener());
    }

    private void reset(){
        chiffreUtils = null;
        outputN.setText(getString(R.string.label_output_n_empty));
        outputPointA.setText(getString(R.string.label_output_point_a_empty));
        outputPointP.setText(getString(R.string.label_output_point_p_empty));
    }

    private class CalculateNClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            try {
                long a = Long.parseLong(inputA.getText().toString());
                long b = Long.parseLong(inputB.getText().toString());
                long gX = Long.parseLong(inputGX.getText().toString());
                long gY = Long.parseLong(inputGY.getText().toString());
                long p = Long.parseLong(inputP.getText().toString());

                EllipticCurve curve = new EllipticCurve(a, b, p);
                Point g = new Point(gX, gY, p);

                chiffreUtils = new EllipticCurveChiffreUtils(curve, g);

                long n = curve.orderOf(g);
                outputN.setText(getString(R.string.label_output_n, n));
            } catch (IllegalArgumentException ignore){
                reset();
            }
        }
    }

    private class CalculatePointAClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            if(chiffreUtils == null) {
                reset();
                return;
            }
            try {
                long alpha = Long.parseLong(inputAlpha.getText().toString());

                Point pointA = chiffreUtils.calculatePublicKey(alpha);
                outputPointA.setText(getString(R.string.label_output_point_a, pointA.getX(), pointA.getY()));
            } catch (IllegalArgumentException ignore){
                reset();
            }
        }
    }

    private class CalculatePointPClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            if(chiffreUtils == null) {
                reset();
                return;
            }
            try {
                long alpha = Long.parseLong(inputAlpha.getText().toString());
                long pointBX = Long.parseLong(inputPointBX.getText().toString());
                long pointBY = Long.parseLong(inputPointBY.getText().toString());

                Point pointB = new Point(pointBX, pointBY, chiffreUtils.getCurve().getP());

                Point pointP = chiffreUtils.calculateSharedPrivateKey(pointB, alpha);
                outputPointP.setText(getString(R.string.label_output_point_p, pointP.getX(), pointP.getY()));
            } catch (IllegalArgumentException ignore){
                reset();
            }
        }
    }
}
