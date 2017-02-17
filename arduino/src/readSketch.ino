#include "FC37.h"
#include "DHT.h"
#include "BMP280.h"

DHT dhtSensor(6, 22);
BMP280 bmpSensor(10, 11, 12, 13);
FC37 rainSensor(A0);

void setup() {
    Serial.begin(115200);
    bmpSensor.begin();
}

void print(char* title, long int value) {
    Serial.print('<');
    Serial.print(title);
    Serial.print(':');
    Serial.print(value);
    Serial.print('>');
}

void loop() {
    //TODO: temperature in Kelvin
    delay(2000);
    print("Temperature", (long int)(dhtSensor.readTemperature()*10 + 273));
    print("Humidity", (long int)(dhtSensor.readHumidity()*10));
    print("Pressure", (long int)(bmpSensor.readPressure()/10));
    print("Rain", (long int)(rainSensor.readRain()));
    Serial.print('\n');
}
