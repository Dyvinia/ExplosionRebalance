# Explosion Control & Rebalance
Simple server-side mod (works in singleplayer as well) that gives you configurable control over explosions by allowing you to toggle griefing, add extra knockback, and/or modify damage for each source.

![gif](https://github.com/user-attachments/assets/809569af-e4d0-46eb-86a7-cb23bd56eda2)

## Config

The mod is highly configurable allowing you to tweak aspects of the knockback and falloff as well as settings for each different type of explosion. You are also able to configure explosions from other mods using the Explosions.Custom.Exploders config option.

<details>
<summary>Click to see default config file</summary>
  
```toml
# Controls aspects of the added knockback
[Knockback]
	# Strength of extra knockback caused by the explosion.
	# Default: 3.0
	KnockbackStrength = 3.0
	# Multiplier of extra knockback caused by the explosion to players.
	# Default: 0.9
	PlayerKnockbackMultiplier = 0.9
	# The amount of upward velocity added by the explosion as part of its knockback.
	# Default: 0.1
	UpwardsKnockback = 0.1

# Controls the falloff used for knockback and damage
[Falloff]
	# Exponent used for calculating the falloff.
	# Default: 2.0
	FalloffExponent = 2.0
	# Adds to the radius when calculating the falloff. Allows for entities near the edge of the explosion to still get some knockback
	# Default: 1.0
	FalloffExtension = 1.0

# Controls aspects of different types of explosions
[Explosions]

	[Explosions.Creeper]
		# Prevents creeper explosions from breaking blocks.
		# Default: true
		DisableCreeperGriefing = true
		# Enables extra knockback for creeper explosions.
		# Default: true
		EnableCreeperKnockback = true
		# Multiplier for extra knockback caused by creeper explosions.
		# Default: 1.0
		CreeperKnockbackMultiplier = 1.0
		# Multiplier for the total damage caused by creeper explosions.
		# Default: 1.0
		CreeperDamageMultiplier = 1.0

	[Explosions.EndCrystal]
		# Prevents end crystal explosions from breaking blocks and setting blocks on fire.
		# Default: false
		DisableEndCrystalGriefing = false
		# Enables extra knockback for end crystal explosions.
		# Default: true
		EnableEndCrystalKnockback = true
		# Multiplier for extra knockback caused by end crystal explosions.
		# Default: 0.75
		EndCrystalKnockbackMultiplier = 0.75
		# Multiplier for the total damage caused by end crystal explosions.
		# Default: 1.0
		EndCrystalDamageMultiplier = 1.0

	[Explosions.Fireball]
		# Prevents fireball explosions from breaking blocks and setting blocks on fire.
		# Default: false
		DisableFireballGriefing = false
		# Enables extra knockback for fireball explosions.
		# Default: true
		EnableFireballKnockback = true
		# Multiplier for extra knockback caused by fireball explosions.
		# Default: 0.75
		FireballKnockbackMultiplier = 0.75
		# Multiplier for the total damage caused by fireball explosions.
		# Default: 1.0
		FireballDamageMultiplier = 1.0

	[Explosions.TNT]
		# Prevents TNT explosions from breaking blocks.
		# Default: false
		DisableTNTGriefing = false
		# Enables extra knockback for TNT explosions.
		# Default: true
		EnableTNTKnockback = true
		# Multiplier for extra knockback caused by TNT explosions.
		# Default: 1.25
		TNTKnockbackMultiplier = 1.25
		# Multiplier for the total damage caused by TNT explosions.
		# Default: 1.0
		TNTDamageMultiplier = 1.0

	# Wither
	[Explosions.Wither]

		[Explosions.Wither.Spawn]
			# Prevents wither spawning explosions from breaking blocks.
			# Default: false
			DisableWitherGriefing = false
			# Enables extra knockback for wither spawning explosions.
			# Default: true
			EnableWitherKnockback = true
			# Multiplier for extra knockback caused by wither spawning explosions.
			# Default: 2.0
			WitherKnockbackMultiplier = 2.0
			# Multiplier for the total damage caused by wither spawning explosions.
			# Default: 1.0
			WitherDamageMultiplier = 1.0

		[Explosions.Wither.Skull]
			# Prevents wither skull explosions from breaking blocks.
			# Default: false
			DisableWitherSkullGriefing = false
			# Enables extra knockback for wither skull explosions.
			# Default: true
			EnableWitherSkullKnockback = true
			# Multiplier for extra knockback caused by wither skull explosions.
			# Default: 0.75
			WitherSkullKnockbackMultiplier = 0.75
			# Multiplier for the total damage caused by wither skull explosions.
			# Default: 1.0
			WitherSkullDamageMultiplier = 1.0

	[Explosions.Custom]
		# Allows for adding explosions from other mods.
		# Format: [EntityID, DisableGriefing, EnableKnockback, KnockbackMultiplier, DamageMultiplier]
		# Example: [["entity.minecraft.creeper", true, true, 1.0, 1.0], ["entity.minecraft.tnt", false, true, 1.25, 1.0]]
		Exploders = [[]]
```
</details>
