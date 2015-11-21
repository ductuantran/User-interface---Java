The codes are for implementing a PhotoViewer.

(Double-click on the photo to view its back).

There are 5 main buttons on the Menu bar :

- File : allows to open a photo, delete a photo or quit the program.

- View : allows to select one of three modes to view the photo (not used yet in this version).

- Graphics tools :

	+ Select a color : choose a color in a color chooser. This color can be applied both for text and stroke.

	+ Adjust pen thickness : adjust the thickness of the pen when drawing (by a slider).

	+ Text font : choose a font for the text (both before/after writing). There are now 4 fonts installed : Serif, Sans Serif, AlexBrush-Regular, Pacifico.
	
	+ Text size : used to adjust the size of the text before/after writing.

- Save Back Photo : allows to save (as a photo) what users draw on the back of the original photo.

- Save Project : this allows to save all the information necessary for redrawing the back of the photo.

Once we click this, we can choose a place to save this data file (including all the information of the strokes and texts).

All the data are saved successfully. However, I did not have enough time to implement the fuction in which we read these infos to redraw them.


- Some special features :
        
    	+ When the text is already written on the panel, if we even can change its color or font or size by :

		First choose the color/font/size that we want in the "Graphics Tools".
		
		Second right-click at any position on the text (preferably a little under it) and the change will be applied immediately on the text.

		The effect is immediate.

	+ When currently typing text, we can use "Backspace" key to delete the most recent character (like "real-time" modification?).

	+ When the text is already written, we even can change it position by : 

		First left-click at any position on the text (to trigger the mode).

		Second click again and start dragging. Release mouse when we want to stop.

		The effect is immediate.

	+ When typing text, the program will automatically "enter" if the text may go out of the panel. 

	  The text will be cut so that all the words maintain its entirety. It will find the cutting position automatically.


- All these functions are implemented with Graphics2d in Java Swing WITHOUT using any available complete components.