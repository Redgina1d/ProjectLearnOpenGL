#version 400 core

in vec2 fragTextureCoord;
in vec3 fragPos;

out vec4 fragColour;

uniform sampler2D textureSampler;

vec4 texColor;

void main() {

	texColor = texture(textureSampler, fragTextureCoord);
	if (texColor.a < 0.5) {
		discard;
	}

	fragColour = texColor;

}
