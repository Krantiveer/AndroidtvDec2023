package com.ott.tv.countrycodepicker;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.ott.tv.R;
import com.ott.tv.utils.PreferenceUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

public class StateCodeActivity extends Activity {}/*{

    public static int w = 0, h = 0;
    private Phone_Adp_State adapter = new Phone_Adp_State();
    private GestureDetector mGestureDetector;
    private List<Object[]> alphabet;
    private HashMap<String, Integer> sections = new HashMap<String, Integer>();
    private int sideIndexHeight;
    private static float sideIndexX;
    private static float sideIndexY;
    private int indexListSize;
    public static HashMap<String, String> name_code;
    EditText search;
    //	TextView search_tv, phone_Header;
//	TextView cross_search;
    int sear = 0;
    InputMethodManager imm;
    String[] CountryName = {"Andhra Pradesh", "Arunachal Pradesh"};
    ArrayList<String> temp_countries;
    String[] CountryCode = {"31", "1"};
    List<String> countries;
    ListView lv;
    LinearLayout search_view;
    LinearLayout phone_bac_layout;

    class SideIndexGestureListener extends
            GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                float distanceX, float distanceY) {
            sideIndexX = sideIndexX - distanceX;
            sideIndexY = sideIndexY - distanceY;
            if (sideIndexX >= 0 && sideIndexY >= 0) {
                displayListItem();
            }
            return super.onScroll(e1, e2, distanceX, distanceY);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_codes);

        Display dis = getWindowManager().getDefaultDisplay();
        w = dis.getWidth();
        h = dis.getHeight();

        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        lv = (ListView) findViewById(R.id.listie);
        lv.setItemsCanFocus(true);
        mGestureDetector = new GestureDetector(this,
                new SideIndexGestureListener());

        name_code = new HashMap<String, String>();
        for (int i = 0; i < CountryName.length; i++) {
            CountryCode[i] = "+" + CountryCode[i];
            name_code.put(CountryName[i], CountryCode[i]);
        }
//
//		phone_bac_layout = (LinearLayout) findViewById(R.id.phone_bac_layout);
//		phone_bac_layout.setPadding(w/32, w/32, w/32, w/32);
//		phone_bac_layout.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				finish();
//			}
//		});

//		phone_Header = (TextView) findViewById(R.id.phone_pro_header);
//		phone_Header.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (w * 0.048));
        countries = populateCountries(CountryName);
        create_view(countries);

        search_view = (LinearLayout) findViewById(R.id.search_view);
        LinearLayout.LayoutParams pam_ll = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, (int) (h * 0.08));
//		search_view.setLayoutParams(pam_ll);

        search = (EditText) findViewById(R.id.searchView1);
//		search.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (w * 0.04));
//		search_tv = (TextView) findViewById(R.id.textView1);
//		search_tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (w * 0.04));
//		cross_search = (TextView) findViewById(R.id.imageView1);
//		cross_search.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (w * 0.038));

        search_view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                search.setVisibility(View.VISIBLE);
//				cross_search.setVisibility(View.VISIBLE);
                search.requestFocus();
                search.setText("");
//				search_tv.setVisibility(View.INVISIBLE);
                imm.showSoftInput(search, 0);
            }
        });

//		cross_search.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				search.setVisibility(View.INVISIBLE);
//				cross_search.setVisibility(View.INVISIBLE);
//				search_tv.setVisibility(View.VISIBLE);
//				imm.hideSoftInputFromWindow(search.getApplicationWindowToken(),
//						0);
//
//				countries = populateCountries(CountryName);
//				create_view(countries);
//			}
//		});

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

                // TODO Auto-generated method stub
                String searString = search.getText().toString().toLowerCase();
                if (searString.trim().length() == 0) {
                    countries = populateCountries(CountryName);
                    create_view(countries);
                } else {
                    temp_countries = new ArrayList<String>();
                    for (int i = 0; i < CountryName.length; i++) {
                        if (CountryName[i].toLowerCase().contains(searString)) {
                            temp_countries.add(CountryName[i]);
                        }
                    }
                    String[] arr = new String[temp_countries.size()];
                    for (int j = 0; j < temp_countries.size(); j++)
                        arr[j] = temp_countries.get(j);

                    countries = populateCountries(arr);
                    create_view(countries);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });

        lv.setOnItemClickListener((parent, view, position, id) -> {
            // TODO Auto-generated method stub
            if (adapter.getItemViewType(position) == 0) {
                Log.e("clicked", "item");
                Phone_Adp_State.Item it = (Phone_Adp_State.Item) adapter
                        .getItem(position);
			*//*	Intent i = new Intent();
				i.putExtra("Country_Name", it.text);
				i.putExtra("Country_code", name_code.get(it.text));
				startActivity(i);*//*
                Log.e("clicked", "section"+it.text+name_code.get(it.text));
                PreferenceUtils.getInstance().setUvtv_state_namePref(getApplicationContext(), it.text);
                //PreferenceUtils.getInstance().setCountyCodePref(getApplicationContext(), name_code.get(it.text));
                finish();
                //Log.e("clicked", "item"+RESULT_OK+i);
            } else {
                Log.e("clicked", "section");
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mGestureDetector.onTouchEvent(event)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void updateList() {
//		LinearLayout sideIndex = (LinearLayout) findViewById(R.id.sideIndex);
//		sideIndex.removeAllViews();
//		indexListSize = alphabet.size();
//		if (indexListSize < 1) {
//			return;
//		}

//		int indexMaxSize = (int) Math.floor(sideIndex.getHeight() / 20);
        int tmpIndexListSize = indexListSize;
//		while (tmpIndexListSize > indexMaxSize) {
//			tmpIndexListSize = tmpIndexListSize / 2;
//		}
//		double delta;
//		if (tmpIndexListSize > 0) {
//			delta = indexListSize / tmpIndexListSize;
//		} else {
//			delta = 1;
//		}

//		TextView tmpTV;
//		for (double i = 1; i <= indexListSize; i = i + delta) {
//			Object[] tmpIndexItem = alphabet.get((int) i - 1);
//			String tmpLetter = tmpIndexItem[0].toString();
//
//			tmpTV = new TextView(this);
//			tmpTV.setText(tmpLetter);
//			tmpTV.setTextColor(Color.parseColor("#000000"));
//			tmpTV.setText(tmpLetter);
//			tmpTV.setGravity(Gravity.CENTER_VERTICAL);
//			// tmpTV.setTextSize(15);
//			tmpTV.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (w * 0.035));
//			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//					ViewGroup.LayoutParams.WRAP_CONTENT,
//					ViewGroup.LayoutParams.WRAP_CONTENT, 1);
////			tmpTV.setLayoutParams(params);
//
////			sideIndex./=addView(tmpTV);
//		}

//		sideIndexHeight = sideIndex.getHeight();

//		sideIndex.setOnTouchListener(new OnTouchListener() {
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				// now you know coordinates of touch
//				sideIndexX = event.getX();
//				sideIndexY = event.getY();
//
//				// and can display a proper item it country list
//				displayListItem();
//
//				return false;
//			}
//		});
    }

    public void displayListItem() {
//		LinearLayout sideIndex = (LinearLayout) findViewById(R.id.sideIndex);
//		sideIndexHeight = sideIndex.getHeight();
        // compute number of pixels for every side index item
        double pixelPerIndexItem = (double) sideIndexHeight / indexListSize;

        // compute the item index for given event position belongs to
        int itemPosition = (int) (sideIndexY / pixelPerIndexItem);

        // get the item (we can do it since we know item index)
        if (itemPosition < alphabet.size()) {
            Object[] indexItem = alphabet.get(itemPosition);
            int subitemPosition = sections.get(indexItem[0]);

            // ListView lvSearch = (ListView) findViewById(android.R.id.list);
            lv.setSelection(subitemPosition);
        }
    }

    public void create_view(List<String> countries) {
        Log.e("countries", "are " + countries);
        Collections.sort(countries);
        alphabet = new ArrayList<Object[]>();
        lv.invalidate();
        List<Phone_Adp_State.Row> rows = new ArrayList<Phone_Adp_State.Row>();
        int start = 0;
        int end = 0;
        String previousLetter = null;
        Object[] tmpIndexItem = null;
        Pattern numberPattern = Pattern.compile("[0-9]");

        for (String country : countries) {
            String firstLetter = country.substring(0, 1);

            // Group numbers together in the scroller
            if (numberPattern.matcher(firstLetter).matches()) {
                firstLetter = "#";
            }

            // If we've changed to a new letter, add the previous letter to the
            // alphabet scroller
            if (previousLetter != null && !firstLetter.equals(previousLetter)) {
                end = rows.size() - 1;
                tmpIndexItem = new Object[3];
                tmpIndexItem[0] = previousLetter.toUpperCase(Locale.UK);
                tmpIndexItem[1] = start;
                tmpIndexItem[2] = end;
                alphabet.add(tmpIndexItem);

                start = end + 1;
            }

            // Check if we need to add a header row
            if (!firstLetter.equals(previousLetter)) {
                rows.add(new Phone_Adp().Section(firstLetter));
                sections.put(firstLetter, start);
            }

            // Add the country to the list
            rows.add(new Phone_Adp_State.Item(country));
            previousLetter = firstLetter;
        }

        if (previousLetter != null) {
            // Save the last letter
            tmpIndexItem = new Object[3];
            tmpIndexItem[0] = previousLetter.toUpperCase(Locale.UK);
            tmpIndexItem[1] = start;
            tmpIndexItem[2] = rows.size() - 1;
            alphabet.add(tmpIndexItem);
        }
        adapter = new Phone_Adp_State();
        adapter.setRows(rows);

        lv.setAdapter(adapter);

        updateList();
    }

    private List<String> populateCountries(String[] country_name) {
        List<String> countries = new ArrayList<String>();
        for (int i = 0; i < country_name.length; i++) {
            countries.add(country_name[i]);
        }
        return countries;
    }

}*/