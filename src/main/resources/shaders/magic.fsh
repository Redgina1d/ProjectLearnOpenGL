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

	fragColour = texColor + vec4(clamp(sin(fragPos.y), 0.2, 0.8), clamp(sin(fragPos.x), 0.2, 0.8), clamp(sin(fragPos.z), 0.2, 0.8), 1);
	fragColour = mix(vec4(skyColour, 1.0), fragColour, visibility);
}
