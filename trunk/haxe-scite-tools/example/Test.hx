import flash.display.Sprite;
import flash.display.BitmapData;
import flash.display.Bitmap;
import flash.text.TextField;
import flash.events.Event;
import flash.display.StageDisplayState;
import flash.events.KeyboardEvent;
import flash.events.MouseEvent;
import flash.ui.Keyboard;


class Test extends Sprite {
	static var frame : Int = 0;
	static inline var framerate : Int = 25;
	public var output : BitmapData;
	public var outBmp : Bitmap;
	private var display_txt : TextField;
	private var startTime : Int;

	static function main() {
		var app : Test = new Test();
		flash.Lib.current.addChild(app);
	}

	private function new() {
		super();

		var suse : String;

		output = new BitmapData(400,200,false,0xffff0000);
		outBmp = new Bitmap(output);
		addChild(outBmp);
		
		
		
		display_txt = new TextField();
		addChild(display_txt);
		this.setChildIndex(outBmp,0);
		display_txt.textColor = 0x999999;

		startTime = flash.Lib.getTimer();

		this.addEventListener(Event.ENTER_FRAME, render);
		this.addEventListener(KeyboardEvent.KEY_UP, stage_onKeyUp);
		display_txt.addEventListener(MouseEvent.CLICK, clickHandler);
	}

	private function stage_onKeyUp( evt : KeyboardEvent ) {
		switch( evt.keyCode ) {
		case Keyboard.F11:
			flash.Lib.current.stage.displayState = StageDisplayState.FULL_SCREEN;
		}
	}

	function clickHandler(event : MouseEvent) {
		flash.Lib.current.stage.displayState = StageDisplayState.FULL_SCREEN;
	}

	public function render(event : Dynamic) {
		var fps : Float = 1000*frame/(flash.Lib.getTimer()-startTime+1);
		display_txt.text = ""+frame+" "+Math.round(fps);
		for(x in 0 ... 400) {
			for(y in 0 ... 200) {
				output.setPixel(x,y,x+frame);
			}
		}
		frame++;
	}

}