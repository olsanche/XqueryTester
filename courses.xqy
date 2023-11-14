for $x in doc("TrayTracker.xml")/StationList/Station
where $x/Gate/Trigger/Param[@name='PlcId']
let $unique-items:=$x/Gate/Trigger/Param[@name='PlcId']/text()
return 
	for $item in $unique-items
	return $item