//precache script for use
use test;

//comment
global testglob = "TEST STRING GLOBAL";
var testloc = "TEST STRING LOCAL";
echo #testglob;
echo $testloc;

//built in functions
var rint = @rand[10];

//wait ms
wait 2.5;

//if statement
if exp1 == exp2
	echo "HELLO";
	echo "HELLO FROM IF";
end if

//self-defined functions
function randfunc
	return @add[@rand[arg1],@rand[arg2],@rand[arg3]];
end function

//call self defined functions
var rint = &randfunc[10];
echo $rint;

//set vars
set $var = expression;

//remove vars
destroy $var;
destroy #var;

//run script
exec test;

//call function from other script
&funcname[args];