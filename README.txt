App4 Movie Rating System

Jonathan Peng email:jspeng@csu.fullerton.edu
Jason Han email: jasonhan0426@csu.fullerton.edu
Peter Vu  email: eclipseraid@csu.fullerton.edu

Summary: Read from the sqlite database to get the movie's
name, date and rating and show the data in the listview
of"Most Recent" and "Highest Rating" tab. If the user
click on most recent tab, then the listview will show
the movie by date from newest to oldest. If the user
click on highest rating tab, then the listview will
show the movie by highest rating to lowest. User can
add new movie's name, date and rating or edit them
by pressing on them on the listview. If the "Ask 
Later" checkbox is checked, then the application
will send user a notification to rate the movie.

Documentation: 

-Before running the application, please put the 
taskDB.sqlite file (from the assets folder [ to get to 
assets folder, App4->app->src->main]) into the emulator.
Need to turn on the emulator first, then click 
Tools->Android->Android Device Manager. When the android
device manager is on, click on the emulator, then on the
right side click the folder data->data->com.cpsc411.app4
(the package name) -> databases (if there is no 
databases folder then create the folder by clicking the 
green plus) and then push the database into the 
databases folder by clicking on the phone icon near the
green plus sign.

-The action bar does not show up when there are tabs,
so I changed the theme from Theme.Appcompat. to 
android.Theme.Holo.Light.DarkActionBar. After I changed
it, the add/edit icon does not show on the action bar,
but the word "Add" show in the option menu. 

