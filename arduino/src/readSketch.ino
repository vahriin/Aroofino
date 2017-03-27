#include "FC37.h"
#include "DHT.h"
#include "BMP280.h"

DHT dhtSensor(4, 22);
BMP280 bmpSensor(10, 11, 12, 13);
FC37 rainSensor(A0);

const int shower = 400;
const int rain = 800;
const int drizzle = 1000;
const int dry = 1024;

void setup() {
    Serial.begin(9600);
    bmpSensor.begin();
    delay(1000);
}

void loop() {
    printBMPTemperature();
    printHumidity();
    printPressure();
    printRain();
    Serial.println();
    delay(5000);
}

void printDHTTemperature() {
    Serial.print("<");
    Serial.print("temperature");
    Serial.print(":");
    Serial.print(dhtSensor.readTemperature() + 273);
    Serial.print(">");
}

void printBMPTemperature() {
    Serial.print("<");
    Serial.print("temperature");
    Serial.print(":");
    Serial.print(bmpSensor.readTemperature() + 273);
    Serial.print(">");
}

void printHumidity() {
    Serial.print("<");
    Serial.print("humidity");
    Serial.print(":");
    Serial.print(dhtSensor.readHumidity());
    Serial.print(">");
}

void printPressure() {
    Serial.print("<");
    Serial.print("pressure");
    Serial.print(":");
    Serial.print(bmpSensor.readPressure() + 273);
    Serial.print(">");
}

void printRain() {
    Serial.print("<");
    Serial.print("rain");
    Serial.print(":");
    
    int currentRain = rainSensor.readRain();
    
    if (currentRain < shower) {
        Serial.print("shower");
    } else if (currentRain < rain) {
        Serial.print("rain");
    } else if (currentRain < drizzle) {
        Serial.print("drizzle");
    } else if (currentRain < dry) {
        Serial.print("dry");
    }
    
    Serial.print(">");
}

/*
void loop() {
    //TODO: temperature in Kelvin
    delay(2000);
    print("Temperature", (long int)(dhtSensor.readTemperature()*10 + 2730));
    print("Humidity", (long int)(dhtSensor.readHumidity()*10));
    print("Pressure", (long int)(bmpSensor.readPressure()));
    print("Rain", (long int)(rainSensor.readRain()));
    Serial.print('\n');
}*/
