#include <ESP32Servo.h>
#include <WiFi.h>
#include <PubSubClient.h>

// Pins para los sensores sónicos
#define TRIGPIN1 2
#define ECHOPIN1 4
#define TRIGPIN2 16
#define ECHOPIN2 17

// A partir de qué distancia se detectará un coche aparcado
#define UMBRAL 15

#define VELSONIDO 0.034 // En cm/microsegundo

#define LEDVERDEPIN1 27
#define LEDROJOPIN1 25
#define LEDVERDEPIN2 33
#define LEDROJOPIN2 32

#define SERVOPIN 15

char* ssid = "EUCALIPTO 2g";
const char* password = "LinuxEsComoWindows99";
//ne plaza2_topic "plaza2"

const char* mqttServer = "192.168.0.167";
const int mqttPort = 1883;
const char* mqttUser = "user";
const char* mqttPassword = "pass";

#define plaza1_topic "plaza1"
#define plaza2_topic "plaza2"

bool ocupadaAntes1 = false;
bool ocupadaAntes2 = false;

unsigned long timee;

WiFiClient espClient;
PubSubClient client(espClient);

Servo servo;
void setup()
{
  Serial.begin(115200);
  Serial.println("\nIniciando wifi\n");
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
  delay(2000);
  abrirServo();
  
  Serial.println("SETUP DONE");
  timee = millis();
}

  
void loop()
{
  if(!client.connect())
  {
    reconnect();
  }
  // Funcionamiento de los sensores y leds ---------
  iniTrigger(TRIGPIN1);
  alertas(LEDROJOPIN1, LEDVERDEPIN1, ECHOPIN1, TRIGPIN1, plaza1_topic, &ocupadaAntes1);
  
  iniTrigger(TRIGPIN2);
  alertas(LEDROJOPIN2, LEDVERDEPIN2, ECHOPIN2, TRIGPIN2, plaza2_topic, &ocupadaAntes2);
  //---------------------------------------------------------

  delay(1000);
  client.loop();
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
  client.setCallback(callback);
  while(!client.connected()) {
    if (client.connect("ESP32Client", mqttUser, mqttPassword)) {
      Serial.println("Connected to MQTT");
      client.subscribe("/servo");
      Serial.println("Subscribed to servo");
    } else {
      Serial.print("\nFailed with state ");
      Serial.print(client.state());
      delay(2000);
    }
  }

  client.publish("esp/test","Hello from ESP32");
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

void alertas(int ledRojo, int ledVerde, int echo, int trigger, char* topic, bool* ocupadaAntes){
  float distancia = calcDistancia(echo);
  
  if (distancia >= UMBRAL){
    // Encendemos el LED verde(plaza libre no hay coche)       
    digitalWrite(ledRojo, LOW);       
    digitalWrite(ledVerde, HIGH);
    if (*ocupadaAntes) {
      client.publish(topic, "Libre");
      *ocupadaAntes = false;
    }
  } else {
    digitalWrite(ledRojo, HIGH);       
    digitalWrite(ledVerde, LOW); 
    if (!*ocupadaAntes) {
      client.publish(topic, "Ocupada");
      *ocupadaAntes = true;
    }
  }
}

void reconnect() {
   while (!client.connected()) {
    Serial.print("Attempting MQTT connection...");
    if (client.connect("ESP32Client", mqttUser, mqttPassword)) {
      Serial.println("connected");
      // Subscribe
      client.subscribe("/servo");
    } else {
      Serial.print("failed, rc=");
      Serial.print(client.state());
      Serial.println(" try again in 5 seconds");
      // Wait 5 seconds before retrying
      delay(5000);
    }
  }
}
