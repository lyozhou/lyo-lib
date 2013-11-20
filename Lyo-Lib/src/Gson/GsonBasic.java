package Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

/*----------------------------------------------------------------
 *  Author:        Lyo Zhou
 *  Written:       2013Äê11ÔÂ20ÈÕ       
 *        
 * 
 *  Purpose: 
 *  Some Gson usage
 *  deserialization / serialization object | with null value
 *  deserialization / serialization Array
 *	deserialization / serialization List
 *  deserialization / serialization Generic Types
 *  deserialization / serialization Collection with Objects of Arbitrary Types
 *  prettyPrinting
 *  null print
 *----------------------------------------------------------------*/

public class GsonBasic {
	public static void main(String[] args) {
		// serialization object
		BagOfPrimitives obj = new BagOfPrimitives(1, "abc", 3);
		Gson gson = new Gson();
		String json = gson.toJson(obj);
		System.out.println("serialization's result = " + json);

		// serialization object with null value
		BagOfPrimitives obj1 = new BagOfPrimitives(1, null, 3);
		String json1 = gson.toJson(obj1);
		System.out.println("serialization with null value result = " + json1);

		// deserialization object
		String json2 = "{'value1':1,'value2':'abc'}";
		BagOfPrimitives obj2 = gson.fromJson(json2, BagOfPrimitives.class);
		System.out.println("deserialization's value2 = " + obj2.getValue2());

		// deserialization object with null value
		String json3 = "{'value1':1}";
		BagOfPrimitives obj3 = gson.fromJson(json3, BagOfPrimitives.class);
		System.out.println("deserialization with null value2 = "
				+ obj3.getValue2());

		System.out.println("*****************************");
		// serialization Array
		int[] ints = { 1, 2, 3, 4, 5 };
		String[] strings = { "abc", "def", "ghi" };
		String intsGson = gson.toJson(ints);
		System.out.println("serialization Array result = "
				+ gson.toJson(strings));

		// deserialization Array
		int[] ints2 = gson.fromJson(intsGson, int[].class);
		System.out.println("deserialization Array first element result = "
				+ ints2[0]);

		System.out.println("*****************************");

		// serialization List (!! cant using init method to define value of
		// intsList)
		List<Integer> intsList = new ArrayList<Integer>();
		intsList.add(2);
		intsList.add(2);
		intsList.add(2);
		intsList.add(2);
		String jsonList = gson.toJson(intsList);
		System.out.println("serialization List result = " + jsonList);

		// deserialization List
		Type collectionType = new TypeToken<ArrayList<Integer>>() {
		}.getType();
		ArrayList<Integer> intsList2 = gson.fromJson(jsonList, collectionType);
		System.out.println("deserialization List result = " + intsList2);

		System.out.println("*****************************");

		// serialization Generic Types
		Foo<BagOfPrimitives> fb = new Foo<BagOfPrimitives>(
				new BagOfPrimitives());
		String generic = gson.toJson(fb);
		System.out.println("serialization Generic Types result = " + generic);

		// deserialization Generic Types
		Type fooType = new TypeToken<Foo<BagOfPrimitives>>() {
		}.getType();
		Foo<BagOfPrimitives> tt = gson.fromJson(generic, fooType);
		System.out.println("deserialization Generic Types result = "
				+ tt.getValue().getValue1());
		
		
		System.out.println("*****************************");
		
		// serialization Collection with Objects of Arbitrary Types
		Collection collection = new ArrayList();
		collection.add("hello");
		collection.add(5);
		collection.add(new Event("GREETINGS", "guest"));
		String jsonArbitrary = gson.toJson(collection);
		System.out.println("serialization Collection with Objects of Arbitrary Types result = "+jsonArbitrary);
		
		// deserialization Collection with Objects of Arbitrary Types
		JsonParser parser = new JsonParser();
		JsonArray array = parser.parse(jsonArbitrary).getAsJsonArray();
		String message = gson.fromJson(array.get(0), String.class);
		Event event = gson.fromJson(array.get(2), Event.class);
		System.out.printf("Using Gson.fromJson() to get: %s, %s", message,  event);
		
		// using prettyPrinting to see a beautiful JSON print
		Gson gsonPretty = new GsonBuilder().setPrettyPrinting().create();
		String jsonOutput = gsonPretty.toJson(collection);
		System.out.println(jsonOutput);
		
		// Print null value
		Gson gsonNull = new GsonBuilder().serializeNulls().create();
		String jsonNullPrint = gsonNull.toJson(obj1);
		System.out.println("Print null value result ="+jsonNullPrint);
	}
}

class Event {
	private String name;
	private String source;

	public Event(String name, String source) {
		this.name = name;
		this.source = source;
	}

	@Override
	public String toString() {
		return String.format("(name=%s, source=%s)", name, source);
	}
}

class Foo<T> {
	T value;

	public Foo(T t) {
		this.value = t;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

}

class BagOfPrimitives {
	private int value1;
	private String value2;
	private transient int value3;

	public BagOfPrimitives() {
		this.value1 = 1;
		this.value2 = "abc";
		this.value3 = 2;
	}

	public BagOfPrimitives(int value1, String value2, int value3) {
		this.value1 = value1;
		this.value2 = value2;
		this.value3 = value3;
	}

	public int getValue1() {
		return value1;
	}

	public void setValue1(int value1) {
		this.value1 = value1;
	}

	public String getValue2() {
		return value2;
	}

	public void setValue2(String value2) {
		this.value2 = value2;
	}

	public int getValue3() {
		return value3;
	}

	public void setValue3(int value3) {
		this.value3 = value3;
	}

}
