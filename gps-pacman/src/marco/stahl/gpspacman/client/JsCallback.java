/**
 * 
 */
package marco.stahl.gpspacman.client;

interface JsCallback<T> {
	  void onFailure(String caught);
	  void onSuccess(T result);
}