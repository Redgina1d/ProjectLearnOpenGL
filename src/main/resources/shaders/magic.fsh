#version 400 core

in vec2 fragTextureCoord;
in vec3 fragNormal;
in vec3 fragPos;
in vec3 surfaceNormal;
in float visibility;

out vec4 fragColour;

uniform sampler2D textureSampler;
uniform vec3 skyColour;

vec4 texColor;

void main() {

	texColor = texture(textureSampler, fragTextureCoord);
	if (texColor.a < 0.5) {
		discard;
	}

	fragColour = texColor + vec4(cos(fragPos.x), 1, 1, 1);
	fragColour = mix(vec4(skyColour, 1.0), fragColour, visibility);
}
