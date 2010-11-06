/**
 * 
 */
package marco.stahl.gpspacman.client.georss;

public interface JsCallback<T> {
	  void onFailure(String caught);
	  void onSuccess(T result);
}