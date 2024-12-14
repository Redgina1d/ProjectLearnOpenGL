#version 400 core

in vec3 position;
in vec2 textureCoord;
in vec3 normal;

out vec2 fragTextureCoord;
smooth out vec3 fragNormal;
out vec3 fragPos;
out vec3 surfaceNormal;
out vec3 toLightVector;
out vec3 toCameraVector;

uniform mat4 transformationMatrix;
uniform mat4 projMatrix;
uniform mat4 viewMatrix;
uniform vec3 lightPosition;

void main() {
	vec4 worldPos = transformationMatrix * vec4(position, 1.0);
	gl_Position = projMatrix * viewMatrix * worldPos;
	
	fragNormal = normalize(worldPos).xyz;
	fragPos = worldPos.xyz; 
	fragTextureCoord = textureCoord;
	
	surfaceNormal = (transformationMatrix * vec4(normal, 0.0)).xyz;
	toLightVector = lightPosition - worldPos.xyz;
	toCameraVector = (inverse(viewMatrix) * vec4(0.0,0.0,0.0,1.0)).xyz - worldPos.xyz;
}