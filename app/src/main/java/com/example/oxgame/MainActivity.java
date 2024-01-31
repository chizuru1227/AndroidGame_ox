package com.example.oxgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private ImageButton[] rectButton = new ImageButton[9];
    private boolean senkou = true;
    private int[] button_flag = {9, 9, 9, 9, 9, 9, 9, 9, 9};
    int count = 0;
    Toast toast = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rectButton[0] = (ImageButton) findViewById(R.id.left_top);
        rectButton[1] = (ImageButton) findViewById(R.id.top);
        rectButton[2] = (ImageButton) findViewById(R.id.right_top);
        rectButton[3] = (ImageButton) findViewById(R.id.left);
        rectButton[4] = (ImageButton) findViewById(R.id.center);
        rectButton[5] = (ImageButton) findViewById(R.id.right);
        rectButton[6] = (ImageButton) findViewById(R.id.left_bottom);
        rectButton[7] = (ImageButton) findViewById(R.id.bottom);
        rectButton[8] = (ImageButton) findViewById(R.id.right_bottom);


        resetGame();
    }

    private void resetGame() {
        for (int i = 0; i < 9; i++) {
            rectButton[i].setBackgroundResource(R.drawable.none);
            button_flag[i] = 9;
        }
        senkou = true;
        count = 0;
        //toastのキャンセル
        if (toast != null) toast.cancel();
    }

    public void buttonMethod(View OXButton) {
        count++;
        int i = OXButtonGetId(OXButton);
        ImageButton aImageButton = (ImageButton) OXButton;
        if (count < 10){
            if (button_flag[i] == 9) {
                if (senkou) {
                    aImageButton.setBackgroundResource(R.drawable.maru);
                    senkou = false;
                    button_flag[i] = 0;
                } else {
                    aImageButton.setBackgroundResource(R.drawable.batu);
                    senkou = true;
                    button_flag[i] = 1;
                }
            }
            if (count > 4) {
                Judge();
            }
        } else {
            ToastDisp("リトライしてね");
        }
    }

    private Integer OXButtonGetId(View OXButton) {
        int buttonNum = 9;
        switch (OXButton.getId()) {
            case R.id.left_top:
                buttonNum = 0;
                break;
            case R.id.top:
                buttonNum = 1;
                break;
            case R.id.right_top:
                buttonNum = 2;
                break;
            case R.id.left:
                buttonNum = 3;
                break;
            case R.id.center:
                buttonNum = 4;
                break;
            case R.id.right:
                buttonNum = 5;
                break;
            case R.id.left_bottom:
                buttonNum = 6;
                break;
            case R.id.bottom:
                buttonNum = 7;
                break;
            case R.id.right_bottom:
                buttonNum = 8;
                break;
        }
        return buttonNum;

    }

    private void Judge() {
        boolean win_flag = false;
        int[][] win = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};
        for (int i = 0; i < 8; i++){
            if (button_flag[win[i][0]] != 9 && button_flag[win[i][1]] != 9 && button_flag[win[i][2]] != 9) {
                if (button_flag[win[i][0]] == button_flag[win[i][1]] && button_flag[win[i][1]] == button_flag[win[i][2]]) {
                    if (button_flag[win[i][0]] == 0) ToastDisp("〇の勝ち");
                    if (button_flag[win[i][0]] == 1) ToastDisp("×の勝ち");
                    count = 10;
                    win_flag = true;
                }
            }
        }
        if(count == 9){
            if (!(win_flag)) {
                ToastDisp("引き分け");
            }
        }
    }

    private  void ToastDisp(String message){
        if (toast != null) toast.cancel();
        toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }


    // メニューをActivity上に設置する
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 参照するリソースは上でリソースファイルに付けた名前と同じもの
        getMenuInflater().inflate(R.menu.option, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // メニューが選択されたときの処理
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuItem1:
                resetGame();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
