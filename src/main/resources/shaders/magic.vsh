#version 400 core

in vec3 position;
in vec2 textureCoord;
in vec3 normal;

out vec2 fragTextureCoord;
out vec3 fragNormal;
out vec3 fragPos;
out vec3 surfaceNormal;
out float visibility;

uniform mat4 transformationMatrix;
uniform mat4 projMatrix;
uniform mat4 viewMatrix;

const float density = 0.02;
const float gradient = 1.5;

void main() {

	vec4 worldPos = transformationMatrix * vec4(position, 1.0);
	vec4 posRelativeToCam = viewMatrix * worldPos;
	gl_Position = projMatrix * posRelativeToCam;
	
	fragNormal = normalize(worldPos).xyz;
	fragPos = worldPos.xyz; 
	fragTextureCoord = textureCoord;
	
	surfaceNormal = (transformationMatrix * vec4(normal, 0.0)).xyz;

	float distance = length(posRelativeToCam.xyz);
	visibility = exp(-pow(distance * density, gradient));
	visibility = clamp(visibility, 0.0, 1.0);
}
