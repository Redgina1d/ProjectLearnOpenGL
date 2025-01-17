#version 430 core


in vec3 position;
in vec2 textureCoord;
in vec3 normal;


out vec2 fragTextureCoord;
out vec3 fragNormal;
out vec3 fragPos;
out vec3 surfaceNormal;
out vec3[] toLightVecs;
out vec3 toCamVec;
out float fog;


uniform mat4 trsfMat;
uniform mat4 projMat;
uniform mat4 viewMat;

uniform int lightsNumber;

//uniform vec3 fogParams;

layout(std430, binding = 3) buffer lightPositions {
    vec3 lightPos[];
};

const float density = 0.02;
const float gradient = 1.5;


void main() {

	vec4 worldPos = trsfMat * vec4(position, 1.0);
	vec4 posToCam = viewMat * worldPos;
	gl_Position = projMat * posToCam;
	
	fragNormal = normalize(worldPos).xyz;
	fragPos = worldPos.xyz; 
	fragTextureCoord = textureCoord;
	
	surfaceNormal = (trsfMat * vec4(normal, 0.0)).xyz;

	for (int i = 0; i < lightsNumber; i++) {
		toLightVecs[i] = lightPos[i] - worldPos.xyz;
	}
	
	toCamVec = (inverse(viewMat) * vec4(0.0,0.0,0.0,1.0)).xyz - worldPos.xyz;

	float distance = length(posToCam.xyz);
	fog = exp(-pow(distance * density, gradient));
	fog = clamp(fog, 0.0, 1.0);
}
