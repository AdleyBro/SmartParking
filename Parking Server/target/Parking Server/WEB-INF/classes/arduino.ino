#include <Servo.h>
#include <WiFi.h>
#include <PubSubClient.h>

#define TRIGPIN1 2
#define ECHOPIN1 4
#define TRIGPIN2 16
#define ECHOPIN2 17

#define UMBRAL 15

#define VELSONIDO 0.034 // En cm/segundo

#define LEDVERDEPIN1 27
#define LEDROJOPIN1 25
#define LEDVERDEPIN2 33
#define LEDROJOPIN2 32

#define SERVOPIN 15

#define plaza1_topic "/Parking300/Plaza20/Sensor20"
#define plaza2_topic "/Parking300/Plaza21/Sensor21"

WiFiClient espClient; 
PubSubClient client (espClient);

const char* ssid = "Rock & Roll";
const char* password = "tenretnI%Ls!dA32";

const char* mqttServer = "3.140.225.4";
const int mqttPort = "1883";
const char* mqttUser = "sensor";
const char* mqttPassword = "password";


bool ocupadaAntes1 = false;
bool ocupadaAntes2 = false;

unsigned int time = 0;

WiFiClient espClient;
PubSubClient client(espClient);

Servo servo;
void setup() // BIEN
{
  Serial.begin(115200);
  initWifi();
  initMQTTServer();

  pinMode(ECHOPIN1, INPUT);
  pinMode(TRIGPIN1, OUTPUT);
  pinMode(ECHOPIN2, INPUT);
  pinMode(TRIGPIN2, OUTPUT);
  
  pinMode(LEDVERDEPIN1, OUTPUT);
  pinMode(LEDROJOPIN1, OUTPUT);
  pinMode(LEDVERDEPIN2, OUTPUT);
  pinMode(LEDROJOPIN2, OUTPUT);

  servo.attach(SERVOPIN);
  cerrarServo();
  
  Serial.println("SETUP DONE");
  time = millis();

}

void callback(char* topic, byte* message, unsigned int length) {
  Serial.print("Message arrived on topic: ");
  Serial.print(topic);
  Serial.print(". Message: ");
  String messageTemp;
  
  for (int i = 0; i < length; i++) {
    Serial.print((char)message[i]);
    messageTemp += (char)message[i];
  }
  Serial.println();

  // Feel free to add more if statements to control more GPIOs with MQTT

  // If a message is received on the topic esp32/output, you check if the message is either "on" or "off". 
  // Changes the output state according to the message
  if (String(topic) == "/servo") {
    
    if(messageTemp == "on"){
      abrirServo();
    }
    else if(messageTemp == "off"){
      cerrarServo();
    }
  }
}
  
void loop() // BIEN
{
  if (!client.connected()) {
    reconnect();
  }
  // Funcionamiento de los sensores, leds y buzzers ---------
  iniTrigger(TRIGPIN1);
  alertas(LEDROJOPIN1, LEDVERDEPIN1, ECHOPIN1, TRIGPIN1, plaza1_topic, ocupadaAntes1);
  
  iniTrigger(TRIGPIN2);
  alertas(LEDROJOPIN2, LEDVERDEPIN2, ECHOPIN2, TRIGPIN2, plaza2_topic, ocupadaAntes2);
  //---------------------------------------------------------

  delay(5000);
}

void initWifi() {
  Serial.println("\nIniciando wifi\n");
  Serial.println(ssid);
  WiFi.begin(ssid, password);
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }

  Serial.println("¡Lo logramos, wey!");
  Serial.println(WiFi.localIP());
}

void initMQTTServer() {
  client.setServer(mqttServer, mqttPort);
  client.callback(callback);
  while(!client.connected()) {
    if (client.connect("ESP32Client", mqttUser, mqttPassword)) {
      Serial.println("Connected to MQTT");
    } else {
      Serial.println("Failed with state ");
      Serial.print(client.state());
      delay(2000);
    }
  }
}

void reconnect() {
  // Loop until we're reconnected
  while (!client.connected()) {
    Serial.print("Attempting MQTT connection...");
    // Attempt to connect
    if (client.connect("ESP32Client")) {
      Serial.println("connected");
      // Subscribe
      client.subscribe("/servo");
    } else {
      Serial.print("failed, rc=");
      Serial.print(client.state());
      Serial.println(" try again in 5 seconds");
      delay(5000);
    }
  }
}

void abrirServo() {
  servo.write(90);
}

void cerrarServo() {
  servo.write(0);
}

void colocarServo(int valor) {
  servo.write(valor);
}

void iniTrigger(int trigger) { // BIEN
  digitalWrite(trigger, LOW);
  delayMicroseconds(2);
  
  digitalWrite(trigger, HIGH);
  delayMicroseconds(10);

  digitalWrite(trigger, LOW);
}

float calcDistancia(int echo) { // BIEN
  unsigned long tiempo = pulseIn(echo, HIGH);
  
  int distancia = tiempo * VELSONIDO / 2; // Distancia en valores enteros es suficiente
  
  delay(100);

  return distancia;
}

void alertas(int ledRojo, int ledVerde, int echo, int trigger, char* topic, bool ocupadaAntes){
  float distancia = calcDistancia(echo);
  
  if (distancia >= UMBRAL){
    // Encendemos el LED verde(plaza libre no hay coche)       
    digitalWrite(ledRojo, LOW);       
    digitalWrite(ledVerde, HIGH);
    if (ocupadaAntes = true) {
      client.publish(topic, "Libre");
      ocupadaAntes = false;
    }
  } else {
    digitalWrite(ledRojo, HIGH); 
    digitalWrite(ledVerde, LOW); 
    if (ocupadaAntes = false) {
      client.publish(topic, "Ocupada");
      ocupadaAntes = true;
    }
  }
}