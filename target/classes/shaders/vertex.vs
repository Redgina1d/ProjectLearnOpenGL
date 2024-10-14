#version 400 core

in vec3 position;
in vec2 textureCoord;
in vec3 normal;

out vec2 fragTextureCoord;
out vec3 fragNormal;
out vec3 fragPos;

uniform mat4 transformationMatrix;
uniform mat4 projMatrix;
uniform mat4 viewMatrix;

void main() {
	vec4 worldPos = transformationMatrix * vec4(position, 1.0);
	gl_Position = projMatrix * viewMatrix * worldPos;
	
	fragNormal = normalize(worldPos).xyz;
	fragPos = worldPos.xyz; 
	fragTextureCoord = textureCoord;
}