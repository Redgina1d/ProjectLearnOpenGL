#version 400 core

in vec3 position;
in vec2 textureCoord;

out vec2 fragTextureCoord;
out vec3 fragPos;

uniform mat4 transformationMatrix;

float aspectRatio = 1.7777778;

void main() {
	gl_Position = transformationMatrix * vec4(position.x * aspectRatio, position.y, 0.0, 1.0);
	fragPos = vec3(gl_Position.xy, 1);
	fragTextureCoord = textureCoord;
}