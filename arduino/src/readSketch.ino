#include "FC37.h"
#include "DHT.h"
#include "BMP280.h"

DHT dhtSensor(6, 22);
BMP280 bmpSensor(10, 11, 12, 13);
FC37 rainSensor(A0);

void setup() {
    Serial.begin(9600);
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
    print("Temperature", (long int)(dhtSensor.readTemperature()*10 + 2730));
    print("Humidity", (long int)(dhtSensor.readHumidity()*10));
    print("Pressure", (long int)(bmpSensor.readPressure()));
    print("Rain", (long int)(rainSensor.readRain()));
    Serial.print('\n');
}
