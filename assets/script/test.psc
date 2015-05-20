var test;
set $test 10;

echo $test;

if equal $test 10 and equal 1 1;

end if;

set $test append[$test,100,$test];

destroy $test;

var ent;
set $ent #map->spawn[new_ent,trainer];

